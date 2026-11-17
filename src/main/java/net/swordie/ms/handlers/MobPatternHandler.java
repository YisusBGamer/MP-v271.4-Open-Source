package net.swordie.ms.handlers;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.handlers.header.InHeader;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * In 1351 (mob request; the v271 client's CMob sends it with a subtype). Subtype 0x0e is the pattern selector asking
 * to use a pattern (0x141fd62a0): the client doesn't run it until the server answers with Out 1827 subtype 0x0e
 * (0x141fd9050 -> runner 0x1428b42e0), which every client in the field gets. Subtype 0x12 is a run event the client
 * waits on: 9 at an autoFlip action (0x1428975f0) and 10 at a teleport command (targetPosClientSet) are answered with
 * Out 956 (facing / destination); without the answer the mob never turns and never teleports. Others are logged.
 */
public class MobPatternHandler {
    private static final Logger log = LogManager.getLogger(MobPatternHandler.class);
    private static final int USE_PATTERN = 0x0e;
    private static final int RUN_EVENT = 0x12;
    private static final int EVENT_FLIP = 9;
    private static final int EVENT_TELEPORT = 10;
    // teleport command targetInfo offset: land this far from the target, on the mob's side
    private static final int TELEPORT_GAP = 60;
    // 4 mob, 4 subtype, 4 key, 4 seq, 4 pattern id, 1 flag, 8 mob position, 1, 8 target position
    private static final int USE_BODY = 38;
    private static final int LOG_PER_SUBTYPE = 200;
    // test.patternLogPerMob: use requests logged per mob template (0 = none)
    private static final int LOG_PER_MOB = net.swordie.ms.ServerSettings.getInt("test.patternLogPerMob", 100000);
    private static final Map<Integer, AtomicInteger> logged = new ConcurrentHashMap<>();
    private static final Map<Integer, AtomicInteger> loggedStarts = new ConcurrentHashMap<>();
    private static final AtomicInteger runIds = new AtomicInteger(1);
    // run id -> {pattern id, start time}, for the subtype 0x12 log
    private static final Map<Integer, long[]> runs = java.util.Collections.synchronizedMap(
            new java.util.LinkedHashMap<>(64, 0.75f, false) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, long[]> e) {
                    return size() > 256;
                }
            });
    private static final AtomicInteger runEvents = new AtomicInteger();
    // test.patternSummons=false: SkillData/summon of non-tool patterns spawns nothing
    private static final boolean SUMMONS =
            net.swordie.ms.ServerSettings.getBoolean("test.patternSummons", true);

    @Handler(op = InHeader.MOB_REQUEST)
    public static void handleMobRequest(Char chr, InPacket inPacket) {
        Field field = chr.getField();
        byte[] data = inPacket.decodeArr(inPacket.getUnreadAmount());
        if (field == null || data.length < 8) {
            return;
        }
        ByteBuffer b = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        // the client puts a prefix of varying length (6-12 bytes seen) in front of the body. A use request has a
        // fixed 38-byte body, so try the tail first; otherwise look for a mob of this field followed by a small subtype
        Mob mob = null;
        int tail = data.length - USE_BODY;
        if (tail >= 0 && b.getInt(tail + 4) == USE_PATTERN && field.getLifeByObjectID(b.getInt(tail)) instanceof Mob m) {
            mob = m;
            b.position(tail + 4);
        }
        for (int off = 0; mob == null && off <= 16 && off + 8 <= data.length; off++) {
            int s = b.getInt(off + 4);
            if (s >= 0 && s < 0x40 && field.getLifeByObjectID(b.getInt(off)) instanceof Mob m) {
                mob = m;
                b.position(off + 4);
            }
        }
        if (mob == null) {
            logOnce(-1, "no mob id found", data);
            return;
        }
        int sub = b.getInt();
        if (sub == RUN_EVENT) {
            logRunEvent(chr, mob, b, data);
            return;
        }
        logOnce(sub, "mob " + mob.getTemplateId(), data);
        if (sub != USE_PATTERN || b.remaining() < 4 + 4 + 4 + 1 + 17) {
            return;
        }
        int key = b.getInt();
        int seq = b.getInt();
        int patternId = b.getInt();
        int flag = b.get() & 0xFF;
        byte[] where = new byte[17]; // mob position (8), a byte, target position (8): echoed to the runner
        b.get(where);
        // the run's facing (block byte 8, 1 = left) is the mob's current one; turn it toward the target so actions
        // and rushes without an autoFlip go at the player
        ByteBuffer w = ByteBuffer.wrap(where).order(ByteOrder.LITTLE_ENDIAN);
        if (w.getInt(0) == 0 && w.getInt(4) == 0 && mob.getPosition() != null) {
            // the client leaves the mob's own spot at 0,0; GMS answers with where the mob stands (Akechi -62,55,
            // Lotus 0,-16). Echoed as 0,0 the runner starts the pattern there: Princess No dropped under her floor
            // (811000500 is at y -205) and took hits only there
            w.putInt(0, mob.getPosition().getX());
            w.putInt(4, mob.getPosition().getY());
        }
        where[8] = (byte) (w.getInt(9) < w.getInt(0) ? 1 : 0);
        PatternHotfixHandler.PatternInfo info = PatternHotfixHandler.pattern(patternId);
        int groups = info == null ? 0 : info.actionCount();
        int commands = info == null ? 0 : info.commandCount();
        List<Integer> slots = legacySlots(patternId, info);
        List<int[]> points = legacyPoints(field, patternId, info);
        if (points.isEmpty() && patternId / 100000 == 8000 && patternId % 1000 == 3) {
            // Princess No's skill4 -> skillAfter4 vanish: with no point the client put her at 0,0, under her floor
            // (every later request reported her there). One point: the floor under the target spot (a guess)
            int tx = w.getInt(9), ty = w.getInt(13);
            net.swordie.ms.world.field.Foothold fh = field.findFootHoldBelow(new Position(tx, ty - 30));
            points = List.of(new int[]{tx, fh != null ? fh.getYFromX(tx) : ty});
        }
        int runId = runIds.getAndIncrement();
        runs.put(runId, new long[]{patternId, System.currentTimeMillis()});
        field.broadcastPacket(startPattern(mob, key, runId, patternId, flag, where, groups, commands, slots, points));
        String summoned = info == null || info.tool() ? "" : summon(field, mob, info);
        long expected = net.swordie.ms.life.mob.MobPatternDriver.runStarted(mob, info);
        if (loggedStarts.computeIfAbsent(mob.getTemplateId(), k -> new AtomicInteger()).getAndIncrement() < LOG_PER_MOB) {
            String format = info == null ? "unknown" : info.tool() ? "tool" : "legacy";
            String pts = points.isEmpty() ? "" : ", " + points.size() + " points " + points.stream()
                    .map(p -> p[0] + "," + p[1]).collect(java.util.stream.Collectors.joining(" "));
            log.info(String.format("[Patterns] %s asks %d to use %d (key %d, seq %d, flag %d): run %d started, "
                            + "%s, %d action groups, %d commands, slots %s%s%s, 1827 body %d bytes, next queue in %d ms",
                    chr.getName(), mob.getTemplateId(), patternId, key, seq, flag, runId, format, groups, commands,
                    slots, pts, summoned, startPatternLength(groups, commands, slots.size(), points.size()), expected));
        }
    }

    /**
     * Run +0x98 for a pattern without tool data (builder 0x141f39440). Lotus 1000 patterns 000-002 of every
     * difficulty: callback 0x141f6ef40 starts only when the list has exactly SkillData/x entries; entry i is laser i
     * (started i * z ms in), its range moved by value * y - s. Values 0..x-1: the consecutive sweep the data
     * describes. Lotus 1007 pattern 000: callback 0x141f62d70 runs z2 waves of z strikes; entry wave * z + i is an
     * index into SkillData/point (a wave starts every attackDelay ms). Random points, distinct within a wave.
     * Everything else: empty.
     */
    static List<Integer> legacySlots(int patternId, PatternHotfixHandler.PatternInfo info) {
        if (info == null || info.tool()) {
            return List.of();
        }
        int category = patternId / 100000;
        List<Integer> out = new ArrayList<>();
        if (category == 1000 && patternId % 1000 <= 2 && info.skillX() > 0 && info.skillX() <= 100) {
            for (int i = 0; i < info.skillX(); i++) {
                out.add(i);
            }
        } else if (category == 1007 && patternId % 1000 == 0) {
            int perWave = info.skill("z"), waves = info.skill("z2"), points = info.pointCount();
            if (perWave <= 0 || waves <= 0 || perWave > points || perWave * waves > 200) {
                return List.of();
            }
            List<Integer> all = new ArrayList<>();
            for (int i = 0; i < points; i++) {
                all.add(i);
            }
            for (int w = 0; w < waves; w++) {
                java.util.Collections.shuffle(all);
                out.addAll(all.subList(0, perWave));
            }
        }
        return out;
    }

    /**
     * Run +0xe0 (8-byte points: x, y) for a pattern without tool data. Lotus 1002 pattern 004 (phase 2 field, type
     * 369 only): callback 0x141f6c0d0 places min(SkillData/x, list size) obstacles at these points, two every
     * SkillData/y ms, each a random look of SkillData/s. Random spots on the ground. Everything else: empty.
     */
    static List<int[]> legacyPoints(Field field, int patternId, PatternHotfixHandler.PatternInfo info) {
        if (info == null || info.tool() || patternId / 100000 != 1002 || patternId % 1000 != 4) {
            return List.of();
        }
        int n = Math.min(info.skillX(), 100);
        List<int[]> out = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Position at = field.getRandomPosOnWalkableFoothold(40, true);
            if (at != null) {
                out.add(new int[]{at.getX(), at.getY()});
            }
        }
        return out;
    }

    /**
     * SkillData/summon of a pattern without tool data: the client only plays the effect, the mobs come from the
     * server. count mobs per entry at distinct positions from its list; not while that many of it are alive. With
     * summon/randomCount, only that many entries (picked at random) come out. onlyViewMob props without a Mob.wz
     * removeAfter leave when the pattern ends (Info/lifeTime).
     */
    private static String summon(Field field, Mob boss, PatternHotfixHandler.PatternInfo info) {
        if (!SUMMONS || info.summons().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        List<PatternHotfixHandler.Summon> entries = new ArrayList<>(info.summons());
        int pick = info.summonRandomCount();
        if (pick > 0 && pick < entries.size()) {
            java.util.Collections.shuffle(entries);
            entries = entries.subList(0, pick);
        }
        for (PatternHotfixHandler.Summon s : entries) {
            if (field.getAliveMobCount(s.mobId()) >= s.count()) {
                sb.append(", summon ").append(s.mobId()).append(" skipped (alive)");
                continue;
            }
            List<int[]> spots = new ArrayList<>(s.positions());
            java.util.Collections.shuffle(spots);
            int n = 0;
            for (int i = 0; i < s.count(); i++) {
                int[] at = spots.isEmpty() ? new int[]{boss.getPosition().getX(), boss.getPosition().getY()}
                        : spots.get(i % spots.size());
                Mob m = field.spawnMob(s.mobId(), at[0], at[1], false, 0);
                if (m != null) {
                    n++;
                    if (s.onlyView() && info.lifeTimeMs() > 0 && m.getMobInfo().getRemoveAfter() <= 0) {
                        net.swordie.ms.handlers.executors.EventManager.addEvent(() -> {
                            if (m.isAlive() && m.getField() == field) {
                                m.expire();
                            }
                        }, info.lifeTimeMs());
                    }
                }
            }
            sb.append(", summoned ").append(n).append(" x ").append(s.mobId());
        }
        return sb.toString();
    }

    /** Subtype 0x12: run id, event, argument, then event data (mob position, facing, ...). Events 9/10 answered. */
    private static void logRunEvent(Char chr, Mob mob, ByteBuffer b, byte[] data) {
        if (b.remaining() < 12) {
            return;
        }
        int runId = b.getInt();
        int event = b.getInt();
        int arg = b.getInt();
        // the client's own mob position: the server's copy can lag behind rushes the client ran itself
        Position at = b.remaining() >= 8 ? new Position(b.getInt(), b.getInt()) : mob.getPosition();
        String reply = answerRunEvent(chr, mob, at, runId, event, arg);
        int n = runEvents.incrementAndGet();
        if (n > 40 && n % 100 != 0) {
            return;
        }
        String pos = at == null ? "" : String.format(", pos %d,%d", at.getX(), at.getY());
        String facing = b.remaining() >= 4 ? ", facing " + b.getInt() : "";
        long[] run = runs.get(runId);
        String which = run == null ? "unknown run" : String.format("pattern %d, %d ms after start", run[0],
                System.currentTimeMillis() - run[1]);
        log.info(String.format("[Patterns] run event (#%d) from %s, mob %d: run %d (%s), event %d, arg %d%s%s%s; "
                        + "%d bytes: %s", n, chr.getName(), mob.getTemplateId(), runId, which, event, arg, pos, facing,
                reply, data.length, Util.readableByteArray(data)));
    }

    /** Out 956 for events 9 (face the player; arg = action group) and 10 (teleport next to the player; arg = key). */
    private static String answerRunEvent(Char chr, Mob mob, Position from, int runId, int event, int arg) {
        if (event != EVENT_FLIP && event != EVENT_TELEPORT) {
            return "";
        }
        Position target = chr.getPosition();
        if (target == null || from == null) {
            return "";
        }
        OutPacket out = new OutPacket(OutHeader.MOB_PATTERN_EVENT_RESULT);
        out.encodeInt(mob.getObjectId());
        out.encodeInt(runId);
        out.encodeInt(event);
        String reply;
        if (event == EVENT_FLIP) {
            int left = target.getX() < from.getX() ? 1 : 0;
            out.encodeInt(left);
            out.encodeInt(arg);
            reply = ", answered facing " + left;
        } else {
            int x = target.getX() + (from.getX() < target.getX() ? -TELEPORT_GAP : TELEPORT_GAP);
            int y = target.getY();
            // land on the ground under that spot (or under the player), never past a wall or in the air
            var fh = chr.getField().findFootHoldBelow(new Position(x, y - 50));
            if (fh == null || fh.getX1() == fh.getX2()) {
                x = target.getX();
                fh = chr.getField().findFootHoldBelow(new Position(x, y - 50));
            }
            if (fh != null && fh.getX1() != fh.getX2()) {
                x = Math.max(Math.min(fh.getX1(), fh.getX2()), Math.min(Math.max(fh.getX1(), fh.getX2()), x));
                y = fh.getYFromX(x);
            }
            int left = target.getX() < x ? 1 : 0;
            out.encodeInt(x);
            out.encodeInt(y);
            out.encodeInt(arg);
            out.encodeInt(1);
            out.encodeInt(left);
            mob.setPosition(new Position(x, y));
            reply = String.format(", answered teleport to %d,%d facing %d", x, y, left);
        }
        chr.getField().broadcastPacket(out);
        return reply;
    }

    /**
     * Out 1827 subtype 0x0e, as 0x141fd9050 reads it: header, the 17-byte position block, the run data (0x140cb2080)
     * and the selector's state count. Run data: +0x208 = subAction choice per Action group (0 each; a command whose
     * baseActionIndex has no choice here is skipped), +0x250 = enable flag per Command entry (1 each); every other
     * list empty, except +0x98 (slots) and +0xe0 (points) for patterns without tool data.
     */
    public static OutPacket startPattern(Mob mob, int key, int runId, int patternId, int flag, byte[] where,
                                         int groups, int commands, List<Integer> slots, List<int[]> points) {
        OutPacket out = new OutPacket(OutHeader.MOB_REQUEST_RESULT);
        out.encodeInt(mob.getObjectId());
        out.encodeInt(USE_PATTERN);
        out.encodeInt(key);
        out.encodeInt(runId);
        out.encodeInt(patternId);
        // GMS: where the server has the mob (Princess No -160,-205; Lotus 0,-16 / 515,97). The run starts from here;
        // 0,0 put her under her floor and Lotus's later phases' attacks off the boss
        Position at = mob.getPosition();
        out.encodeInt(at == null ? 0 : at.getX());
        out.encodeInt(at == null ? 0 : at.getY());
        out.encodeInt(flag);
        out.encodeArr(where);
        encodeRunData(out, groups, commands, slots, points);
        // selector states (0x14289a840, read only for some patterns/fields): none
        out.encodeInt(0);
        return out;
    }

    /** Body length of a start reply after the opcode: 49 header, run data, 4 state count. */
    public static int startPatternLength(int groups, int commands, int slots, int points) {
        return 49 + runDataLength(groups, commands, slots, points) + 4;
    }

    /** Run data length: 160 with every list empty, + 4 per Action group, Command and +0x98 slot, + 8 per point. */
    public static int runDataLength(int groups, int commands, int slots, int points) {
        return 160 + 4 * groups + 4 * commands + 4 * slots + 8 * points;
    }

    /** Run data in 0x140cb2080's read order; counts are ints, empty lists still take their count. */
    static void encodeRunData(OutPacket out, int groups, int commands, List<Integer> slots, List<int[]> points) {
        out.encodeLong(0); // +58
        out.encodeInt(0); // +60
        zeros(out, 2); // +68 +80 vectors
        out.encodeInt(points.size()); // +e0 8-byte entries (0x140403970)
        for (int[] p : points) {
            out.encodeInt(p[0]);
            out.encodeInt(p[1]);
        }
        out.encodeInt(slots.size()); // +98 ints (0x140620300)
        for (int v : slots) {
            out.encodeInt(v);
        }
        zeros(out, 4); // +b0 +c8 vectors, +110 +120 maps
        out.encodeInt(0); // +150
        out.encodeInt(0); // +f8 deadlines
        out.encodeInt(0); // +158 strings
        zeros(out, 2); // +170 +174
        out.encodeInt(0); // +190 records
        zeros(out, 5); // +1b0: three vectors, two ints
        zeros(out, 2); // +200 +204
        out.encodeLong(0); // +2b0
        out.encodeInt(groups); // +208 subAction choice per Action group
        zeros(out, groups);
        zeros(out, 3); // +220 +238 vectors, +2b8 vector
        out.encodeInt(commands); // +250 Command enable flags
        for (int i = 0; i < commands; i++) {
            out.encodeInt(1);
        }
        zeros(out, 2); // +268 +280
        out.encodeInt(0); // +298 records
        zeros(out, 4); // +2d0 +2e0 +2f0 +300 maps
        zeros(out, 2); // +1a8 +1ac
    }

    private static void zeros(OutPacket out, int ints) {
        for (int i = 0; i < ints; i++) {
            out.encodeInt(0);
        }
    }

    private static void logOnce(int sub, String what, byte[] data) {
        if (logged.computeIfAbsent(sub, k -> new AtomicInteger()).getAndIncrement() < LOG_PER_SUBTYPE) {
            log.info(String.format("[Patterns] In 1351 subtype %s (%s), %d bytes: %s",
                    sub < 0 ? "?" : "0x" + Integer.toHexString(sub), what, data.length, Util.readableByteArray(data)));
        }
    }
}
