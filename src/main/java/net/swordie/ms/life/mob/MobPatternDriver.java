package net.swordie.ms.life.mob;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.PatternHotfixHandler;
import net.swordie.ms.handlers.PatternHotfixHandler.PatternInfo;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.jwzlib.WzFile;
import net.swordie.ms.jwzlib.WzObject;
import net.swordie.ms.jwzlib.WzProperty;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.instancehelper.boss.LotusV265InstanceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

/**
 * Feeds the client's pattern selector for monsters with Mob.wz info/patternSys. The v271 client never picks a pattern
 * on its own: it only tries the head of a queue the server sends (Out 985), and after each use it waits until the
 * server clears its "used" flag (Out 979). Both go to the monster's controller only, so one client runs the attacks.
 * The server picks a pattern from the set in test.patternFile, honouring each pattern's cooltimeMS / startCooltimeMS,
 * and sends one 979 + 985 pair. It sends the next pair only after the client's request was answered and that run's
 * expected length has passed (985 doesn't cancel a run: repeating the pair lets runs overlap), or when no request
 * came within test.patternRequestWaitMs.
 */
public final class MobPatternDriver {
    private static final Logger log = LogManager.getLogger(MobPatternDriver.class);

    private static final boolean ENABLED =
            net.swordie.ms.ServerSettings.getBoolean("test.patternDrive", true);
    private static final long GAP_MS = net.swordie.ms.ServerSettings.getInt("test.patternGapMs", 3000);
    private static final long FIRST_DELAY_MS = 2000;
    private static final long REQUEST_WAIT_MS =
            net.swordie.ms.ServerSettings.getInt("test.patternRequestWaitMs", 10000);
    // per Action group whose animation isn't found in Mob.wz; added to every run
    private static final int DEFAULT_ACTION_MS = 1500;
    private static final int RUN_SLACK_MS = 1000;
    private static final int MAX_RUN_MS = 20000;
    // test.patternOnly: global pattern ids to restrict the choice to (tests), e.g. 108800000,109100000
    private static final java.util.Set<Integer> ONLY = java.util.Arrays.stream(
                    net.swordie.ms.ServerSettings.getString("test.patternOnly", "").split(","))
            .map(String::trim).filter(s -> s.matches("\\d+")).map(Integer::parseInt)
            .collect(java.util.stream.Collectors.toUnmodifiableSet());

    // template id -> {category, difficulty}, or null-equivalent NONE
    private static final int[] NONE = new int[0];
    private static final Map<Integer, int[]> patternSys = new ConcurrentHashMap<>();
    // "template/action" -> animation length in ms (sum of frame delays), -1 = not found
    private static final Map<String, Integer> animLengths = new ConcurrentHashMap<>();
    private static final java.util.Set<Integer> logged = ConcurrentHashMap.newKeySet();
    // Contributed by Arixal (from GitLab).
    // Mob.wz, opened on first use and kept: its images are only parsed while being read
    private static WzFile mobWz;

    /** Per-monster state. */
    static final class State {
        final long spawnedAt = System.currentTimeMillis();
        final Map<Integer, Long> readyAt = new HashMap<>();
        long nextAt = spawnedAt + FIRST_DELAY_MS;
        Char sentTo;
        int last;
        // a 979 + 985 pair went out and no request was answered yet
        boolean awaiting;
        long pairSentAt;
        long runEndsAt;
        // queued only by the server (queueNow), never picked here
        boolean manual;
        // the last run the server answered: global pattern id, start time
        int runPattern = -1;
        long runAt;
    }

    private MobPatternDriver() {
    }

    static void tick(Mob mob) {
        try {
            drive(mob);
        } catch (RuntimeException e) {
            if (logged.add(-mob.getTemplateId())) {
                log.warn("[Patterns] " + mob.getTemplateId() + ": " + e);
            }
        }
    }

    private static void drive(Mob mob) {
        // every v271 mob with info/patternSys is a boss: other mobs never read their Mob.wz image
        if (!ENABLED || mob.isDead() || mob.getMobInfo() == null || !mob.isBoss()
                || !PatternHotfixHandler.hasPatterns()) {
            return;
        }
        int[] sys = patternSys(mob.getTemplateId());
        if (sys.length == 0) {
            return;
        }
        List<PatternInfo> set = PatternHotfixHandler.patterns(sys[0], sys[1]);
        if (!ONLY.isEmpty()) {
            set = set.stream().filter(p -> ONLY.contains(p.id())).toList();
        }
        Field field = mob.getField();
        if (field != null && !LotusV265InstanceHelper.isAlert(field)) {
            set = set.stream().filter(p -> !LotusV265InstanceHelper.alertOnly(p.id())).toList();
        }
        if (set.isEmpty() || field == null) {
            return;
        }
        State known = mob.getPatternState();
        if (known == null) {
            known = new State();
            mob.setPatternState(known);
        }
        final State st = known;
        long now = System.currentTimeMillis();
        Char controller = field.getLifeToControllers().get(mob);
        if (controller != st.sentTo) {
            if (st.sentTo != null && st.sentTo.getField() == field) {
                st.sentTo.write(queue(mob, -1));
            }
            st.sentTo = controller;
            st.awaiting = false;
            st.nextAt = Math.max(st.nextAt, now + 500);
        }
        if (st.manual || controller == null || now < st.nextAt || now < st.runEndsAt) {
            return;
        }
        if (st.awaiting) {
            if (now < st.pairSentAt + REQUEST_WAIT_MS) {
                return;
            }
            st.awaiting = false;
            if (logged.add(-1_000_000_000 - mob.getTemplateId())) {
                log.info(String.format("[Patterns] %d: no request %d ms after queueing %d; queueing again",
                        mob.getTemplateId(), REQUEST_WAIT_MS, st.last));
            }
        }
        List<PatternInfo> ready = new ArrayList<>();
        for (PatternInfo p : set) {
            long at = st.readyAt.computeIfAbsent(p.id(), k -> st.spawnedAt + p.startCooltimeMs());
            if (at <= now) {
                ready.add(p);
            }
        }
        if (ready.size() > 1) {
            ready.removeIf(p -> p.id() == st.last);
        }
        // a Lotus alert opens with the purge routine ("Commencing purge")
        if (LotusV265InstanceHelper.isAlert(field)
                && ready.stream().anyMatch(p -> LotusV265InstanceHelper.alertOnly(p.id()))) {
            ready.removeIf(p -> !LotusV265InstanceHelper.alertOnly(p.id()));
        }
        if (ready.isEmpty()) {
            st.nextAt = now + 1000;
            return;
        }
        PatternInfo pick = ready.get(ThreadLocalRandom.current().nextInt(ready.size()));
        // + slack: the client starts its own cooldown when it uses the pattern, a little after this pick
        st.readyAt.put(pick.id(), now + Math.max(pick.cooltimeMs(), GAP_MS) + 1500);
        st.last = pick.id();
        st.nextAt = now + GAP_MS;
        st.awaiting = true;
        st.pairSentAt = now;
        controller.write(resume(mob));
        controller.write(queue(mob, pick.id()));
        if (logged.add(mob.getTemplateId())) {
            log.info(String.format("[Patterns] %d (category %d/%02d, %d patterns): first pattern %d sent to %s",
                    mob.getTemplateId(), sys[0], sys[1], set.size(), pick.id(), controller.getName()));
        }
    }

    /**
     * The server answered a use request for this mob: hold the next 979 + 985 pair until the run's expected length
     * has passed. Returns that length in ms.
     */
    public static long runStarted(Mob mob, PatternInfo info) {
        long ms = expectedRunMs(mob.getTemplateId(), info);
        State st = mob.getPatternState();
        if (st != null) {
            long now = System.currentTimeMillis();
            if (info != null) {
                st.runPattern = info.id();
                st.runAt = now;
            }
            st.awaiting = false;
            st.runEndsAt = now + ms;
            st.nextAt = Math.max(st.nextAt, st.runEndsAt);
        }
        return ms;
    }

    /** Leaves this mob's patterns to the server: none is picked until {@link #queueNow} names one. */
    public static void setManual(Mob mob) {
        State st = mob.getPatternState();
        if (st == null) {
            st = new State();
            mob.setPatternState(st);
        }
        st.manual = true;
    }

    /** Sends one 979 + 985 pair for this pattern to the mob's controller now; false without a controller. */
    public static boolean queueNow(Mob mob, int patternId) {
        Field field = mob.getField();
        Char controller = field == null || mob.isDead() ? null : field.getLifeToControllers().get(mob);
        if (controller == null) {
            return false;
        }
        State st = mob.getPatternState();
        if (st != null) {
            st.sentTo = controller;
            st.last = patternId;
            st.pairSentAt = System.currentTimeMillis();
        }
        controller.write(resume(mob));
        controller.write(queue(mob, patternId));
        return true;
    }

    /** The global id of the mob's last run the server answered, or -1. */
    public static int lastRun(Mob mob) {
        State st = mob.getPatternState();
        return st == null ? -1 : st.runPattern;
    }

    /** When that run started (ms), or 0. */
    public static long lastRunAt(Mob mob) {
        State st = mob.getPatternState();
        return st == null ? 0 : st.runAt;
    }

    /**
     * Sum of the pattern's Action group animations (subAction 0, from Mob.wz) plus slack, capped. Without Action
     * groups (patterns without tool data): Info/lifeTime.
     */
    static long expectedRunMs(int templateId, PatternInfo info) {
        long ms = RUN_SLACK_MS;
        if (info == null || info.actions().isEmpty()) {
            return Math.min(ms + (info != null && info.lifeTimeMs() > 0 ? info.lifeTimeMs() : DEFAULT_ACTION_MS),
                    MAX_RUN_MS);
        }
        for (String action : info.actions()) {
            int len = action == null ? -1 : animLength(templateId, action);
            ms += len > 0 ? len : DEFAULT_ACTION_MS;
        }
        return Math.min(ms, MAX_RUN_MS);
    }

    /** Length of a Mob.wz animation (its frames' delays), following info/link once; -1 if not found. */
    static int animLength(int templateId, String action) {
        return animLengths.computeIfAbsent(templateId + "/" + action, k -> {
            int len = readAnimLength(templateId, action);
            if (len < 0) {
                String link = readLink(templateId);
                if (link != null && link.matches("\\d+")) {
                    len = readAnimLength(Integer.parseInt(link), action);
                }
            }
            return len;
        });
    }

    private static int readAnimLength(int templateId, String action) {
        return readMob(templateId, -1, image -> animLengthOf(image, action));
    }

    /** The delays of the animation's frames (its direct children); delays deeper down belong to other things. */
    static int animLengthOf(WzObject<?, ?> image, String action) {
        WzObject<?, ?> animation = image.getChild(action);
        if (animation == null) {
            return -1;
        }
        int total = 0, frames = 0;
        for (WzObject<?, ?> frame : animation) {
            if (frame.getChild("delay") instanceof WzProperty<?> delay) {
                total += delay.getIntValue();
                frames++;
            }
        }
        return frames == 0 ? -1 : total;
    }

    private static String readLink(int templateId) {
        return readMob(templateId, null, MobPatternDriver::linkOf);
    }

    static String linkOf(WzObject<?, ?> image) {
        WzObject<?, ?> link = image.getChildByPath("info/link");
        return link == null ? null : link.toString();
    }

    /**
     * Runs read on the template's Mob.wz image and lets its parsed tree go again. A monster's image is read a few
     * times at most (the results are cached), and all images share one file position, so reads take turns.
     *
     * @return what read returned, or none if the image isn't there or can't be read
     */
    private static synchronized <T> T readMob(int templateId, T none, Function<WzObject<?, ?>, T> read) {
        try {
            if (mobWz == null) {
                mobWz = new WzFile(ServerConstants.WZ_DIR + "/Mob.wz");
            }
            WzObject<?, ?> image = mobWz.getChild(String.format("%07d.img", templateId));
            if (image == null) {
                return none;
            }
            try {
                return read.apply(image);
            } finally {
                image.clear();
            }
        } catch (RuntimeException e) {
            log.warn("[Patterns] can't read Mob.wz " + templateId + ": " + e);
            return none;
        }
    }

    /** Out 979: the client may pick again (it blocks itself after each pattern it starts). */
    private static OutPacket resume(Mob mob) {
        OutPacket out = new OutPacket(OutHeader.MOB_PATTERN_RESUME);
        out.encodeInt(mob.getObjectId());
        return out;
    }

    /** Out 985: the queue the selector reads; it only tries the head. id < 0 sends an empty queue. */
    private static OutPacket queue(Mob mob, int id) {
        OutPacket out = new OutPacket(OutHeader.MOB_PATTERN_QUEUE);
        out.encodeInt(mob.getObjectId());
        if (id < 0) {
            out.encodeInt(0);
        } else {
            out.encodeInt(1);
            out.encodeInt(id);
        }
        return out;
    }

    /** Mob.wz info/patternSys category and difficulty, read once per template. */
    static int[] patternSys(int templateId) {
        return patternSys.computeIfAbsent(templateId, id -> readMob(id, NONE, MobPatternDriver::patternSysOf));
    }

    static int[] patternSysOf(WzObject<?, ?> image) {
        WzObject<?, ?> sys = image.getChildByPath("info/patternSys");
        if (sys == null) {
            return NONE;
        }
        int category = intChild(sys, "category");
        return category > 0 ? new int[]{category, Math.max(0, intChild(sys, "difficulty"))} : NONE;
    }

    private static int intChild(WzObject<?, ?> parent, String name) {
        return parent.getChild(name) instanceof WzProperty<?> value ? value.getIntValue() : 0;
    }
}
