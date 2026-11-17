package net.swordie.ms.client.character.union;

import net.swordie.ms.constants.FieldConstants;
import net.swordie.ms.ServerSettings;
import net.swordie.ms.client.Account;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.jobs.JobManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.connection.packet.ContentPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.model.MessagePacket;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.MobData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.ResetClock;
import net.swordie.ms.loaders.DataTable;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.fieldeffect.FieldEffect;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.ms.world.field.instance.instancehelper.InstanceHelper;
import net.swordie.ms.world.field.Portal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Champion Raid: Draco Isle. Lv. 260+, two or more registered Legion Champions; the squad is the account's
 * champions, played one at a time and swapped with Tab + 1-6. Each champion has its own play time; the raid ends when
 * all of it is used up or stage 6 is cleared. Six stages (993264000-993265000), ten missions each, normal monsters and
 * a War Master / Draco Lord in turn. The score becomes Champion Coins (quest-ex 505005 point): the month's best
 * score counts, coins and scores reset on the 1st.
 * <p>
 * Client (v265.3): the raid manager (0x1421e72b0) keeps one member per slot built from UNION_CHAMPION_INFO 626
 * (part 0 CharacterData, 1 key maps, 2 quick slots, 3 macros). The tag itself is client-side (0x1421ea490 swaps the
 * local user to the member's data), so the server swaps the Char behind the session the same way: the field,
 * instance and mob controllers move to the champion, and the owner comes back when the raid ends.
 * Out 627: 0 squad window, 4 {?, time frozen, tag cooldown ms, ?}, 6 {slot (-1 = end and back to the owner), int,
 * int n, n bytes} swap, 7 swap done (starts the cooldown), 8 slot out of time, 9 pause ms, 10 {char id, ms taken off}.
 * In 1154: int 0 reset, int 1 + int slot + byte forced (the client forces one when the active member runs out).
 */
public class ChampionRaid {
    private static final Logger log = LogManager.getLogger(ChampionRaid.class);

    public static final boolean ENABLED = ServerSettings.getBoolean("championRaid.enabled", true);

    public static final int[] STAGES = {993264000, 993264200, 993264400, 993264600, 993264800, 993265000};
    public static final int STAGING = 993265200;
    public static final int CABIN = 993265400;
    public static final int EXIT = 993265600;
    public static final int MIN_LEVEL = 260;
    public static final int MIN_CHAMPIONS = 2;
    public static final int COIN_QUEST = 505005;
    public static final int COIN_ITEM_NAME = 4310387;
    /** maplestorywiki Legion Champion: each champion can be used for up to 1 minute 40 seconds. */
    public static final int PLAY_MS = 100_000;
    public static final int SETUP_SECONDS = 300;
    private static final int TAG_COOLDOWN_MS = 3000;
    /** The 4th value of GMS's raid config (627 type 4). */
    private static final int CONFIG_LAST = 500;
    private static final int MISSIONS = 10;
    private static final int NORMAL_SCOUTS = 8;
    private static final int NORMAL_BRUTES = 2;
    private static final int NORMAL_POINTS = 40;
    private static final int BOSS_POINTS = 120;
    private static final int BOSS_HP_FACTOR = 10;
    private static final int RETURN_RECORD = 99993265;
    private static final int PAUSE_MS = 600_000;

    /** Per stage: scout, brute, five boss variants (missions 2, 4, 6, 8, 10). */
    private static final Supplier<int[][]> MOBS = DataTable.cached("champion-raid-mobs", t -> t.rows().stream()
            .map(r -> IntStream.concat(IntStream.of(r.i("scout"), r.i("brute")), IntStream.of(r.ints("bosses")))
                    .toArray())
            .toArray(int[][]::new));
    /** Stage element (2215 type 0): 0 acid (Emerald), 1 frozen (Sapphire), 2 flame (Ruby). */
    private static final int[] ELEMENT = {0, 1, 2, 0, 1, 2};

    private enum State {SETUP, FIGHT, CLEARED, ENDED}

    private static final class Member {
        final int slot;
        final Char chr;
        final long posMap;
        int remainingMs = PLAY_MS;

        Member(int slot, Char chr) {
            this.slot = slot;
            this.chr = chr;
            this.posMap = chr.getAvatarData().getCharacterStat().getPosMap();
        }
    }

    private static final class Run {
        final Char owner;
        final Client client;
        final Instance instance;
        final List<Member> members = new ArrayList<>();
        volatile State state = State.SETUP;
        volatile Member active;
        volatile long activeSince;
        volatile long lastTag;
        volatile long outSince;
        int stage;
        int mission;
        int score;
        /** GMS numbers each swap (627 type 6): 1 for the first champion, then up by one, also for the last swap back */
        int swaps;
        final Set<Mob> missionMobs = ConcurrentHashMap.newKeySet();
        boolean started;
        ScheduledFuture<?> tick;
        ScheduledFuture<?> setup;

        Run(Char owner, Instance instance) {
            this.owner = owner;
            this.client = owner.getClient();
            this.instance = instance;
        }

        Member bySlot(int slot) {
            for (Member m : members) {
                if (m.slot == slot) {
                    return m;
                }
            }
            return null;
        }

        Member mostTime(Member except) {
            Member best = null;
            for (Member m : members) {
                if (m != except && m.remainingMs > 0 && (best == null || m.remainingMs > best.remainingMs)) {
                    best = m;
                }
            }
            return best;
        }
    }

    private static final Map<Integer, Run> runsByOwner = new ConcurrentHashMap<>();
    private static final Map<Client, Run> runsByClient = new ConcurrentHashMap<>();
    private static final Map<Integer, Integer> returnMaps = new ConcurrentHashMap<>();

    // ------------------------------------------------------------------------------------------------ entry

    public static boolean isRaidField(int fieldId) {
        for (int id : STAGES) {
            if (id == fieldId) {
                return true;
            }
        }
        return fieldId == STAGING;
    }

    /** Why this character can't start a raid, or null. */
    public static String getEntryBlock(Char chr) {
        if (!ENABLED) {
            return "The Champion Raid is closed at the moment.";
        }
        Account acc = chr.getAccount();
        if (acc == null || !ChampionModule.isUnlocked(chr)) {
            return "Complete the #b[Legion] Champion's Path#k quest first.";
        }
        if (chr.getLevel() < MIN_LEVEL) {
            return "You must be Level " + MIN_LEVEL + " or higher to enter the Champion Raid.";
        }
        if (chr.getParty() != null) {
            return "The Champion Raid is played alone. Leave your party first.";
        }
        if (chr.getInstance() != null || runsByOwner.containsKey(chr.getId())) {
            return "You are already in a raid.";
        }
        if (squad(chr).size() < MIN_CHAMPIONS) {
            return "You need at least " + MIN_CHAMPIONS + " registered Legion Champions to enter.";
        }
        return null;
    }

    private static List<Member> squad(Char owner) {
        List<Member> list = new ArrayList<>();
        Account acc = owner.getAccount();
        ChampionModule.Slot[] slots = ChampionModule.getSlots(acc);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                continue;
            }
            Char c = slots[i].charId == owner.getId() ? owner : acc.getCharById(slots[i].charId);
            if (c != null) {
                list.add(new Member(i, c));
            }
        }
        return list;
    }

    /** Town Sir Hamselot: to the Wanderer's Cabin, remembering where to come back to. */
    public static void toCabin(Char chr) {
        if (chr.getFieldID() != CABIN && !isRaidField(chr.getFieldID())) {
            returnMaps.put(chr.getId(), chr.getFieldID());
            chr.getQuestManager().setHiddenRecord(RETURN_RECORD, String.valueOf(chr.getFieldID()));
        }
        chr.warp(CABIN);
    }

    /** The cabin's door and the exit's Sir Hamselot: back where the character came from. */
    public static void leaveCabin(Char chr) {
        Integer map = returnMaps.remove(chr.getId());
        if (map == null) {
            try {
                String rec = chr.getQuestManager().getHiddenRecord(RETURN_RECORD);
                map = rec == null || rec.isEmpty() ? null : Integer.parseInt(rec);
            } catch (NumberFormatException e) {
                map = null;
            }
        }
        chr.warp(map == null || map <= 0 ? FieldConstants.HENESYS_FIELD_ID : map);
    }

    /** Cabin Sir Hamselot: into the staging grounds (5 minutes to get ready). */
    public static boolean enter(Char chr) {
        String block = getEntryBlock(chr);
        if (block != null) {
            chr.chatMessage(block.replace("#b", "").replace("#k", ""));
            return false;
        }
        chr.getTemporaryStatManager().removeAllStats();
        chr.getScriptManager().warpInstanceIn(STAGING, 0);
        Instance inst = chr.getInstance();
        if (inst == null) {
            return false;
        }
        inst.setForcedReturn(EXIT);
        Run run = new Run(chr, inst);
        run.members.addAll(squad(chr));
        runsByOwner.put(chr.getId(), run);
        runsByClient.put(run.client, run);
        new Helper(run).setup(inst);
        run.setup = EventManager.addEvent(() -> start(run), SETUP_SECONDS * 1000L);
        chr.write(FieldPacket.clock(net.swordie.ms.world.field.ClockPacket.secondsClock(SETUP_SECONDS)));
        notice(chr, String.format("Your squad: %d Champions, %d s each. Talk to Sir Hamselot when you're ready; the "
                + "raid starts by itself in 5 minutes.", run.members.size(), PLAY_MS / 1000));
        return true;
    }

    /** Staging Sir Hamselot (or the setup timer): stage 1. */
    public static void startNow(Char chr) {
        Run run = runsByClient.get(chr.getClient());
        if (run != null) {
            start(run);
        }
    }

    private static synchronized void start(Run run) {
        if (run.started || run.state == State.ENDED) {
            return;
        }
        run.started = true;
        if (run.setup != null) {
            run.setup.cancel(false);
        }
        Char owner = run.owner;
        Field stage1 = run.instance.getField(STAGES[0]);
        if (stage1 == null) {
            end(run, false);
            return;
        }
        owner.warp(stage1);
        Member first = run.bySlot(ChampionModule.getSlotOf(owner));
        if (first == null) {
            first = run.members.get(0);
        }
        // the client builds a member per slot from these
        owner.write(ContentPacket.championRaidReset());
        for (Member m : run.members) {
            for (var p : ContentPacket.championInfoAll(m.slot, m.chr)) {
                owner.write(p);
            }
        }
        owner.write(ContentPacket.championRaidConfig(PLAY_MS, 0, TAG_COOLDOWN_MS, CONFIG_LAST));
        owner.write(ContentPacket.championRaidTagTimer());
        for (Member m : run.members) {
            owner.write(ContentPacket.championRaidTime(m.chr.getId(), -m.remainingMs));
        }
        beginStage(run, 0);
        if (first.chr != owner) {
            swapTo(run, first);
        } else {
            run.active = first;
        }
        owner.write(ContentPacket.championRaidSwap(first.slot, ++run.swaps));
        owner.write(ContentPacket.championRaidSwapDone());
        run.activeSince = System.currentTimeMillis();
        run.lastTag = run.activeSince;
        run.state = State.FIGHT;
        run.tick = EventManager.addFixedRateEvent(() -> tick(run), 1000, 1000);
        nextMission(run);
    }

    private static void beginStage(Run run, int stage) {
        run.stage = stage;
        run.mission = 0;
        Char c = current(run);
        c.write(ContentPacket.championRaidOpen());
        c.write(ContentPacket.championRaidField(0, ELEMENT[stage], stage + 1, 0, 0));
        c.write(ContentPacket.championRaidField(1, stage + 1, run.score, 0));
        c.write(ContentPacket.championRaidPause(0));
        c.write(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz("Effect/ChampionRaid.img/enterStage/" + stage, 0)));
    }

    // ------------------------------------------------------------------------------------------------ missions

    private static Char current(Run run) {
        Member a = run.active;
        return a == null ? run.owner : a.chr;
    }

    private static Field field(Run run) {
        return run.instance.getField(STAGES[run.stage]);
    }

    private static List<Position> spots(Field field) {
        List<Position> res = new ArrayList<>();
        for (Portal p : field.getInfo().getPortals()) {
            if (p.getName() != null && p.getName().startsWith("ph_")) {
                res.add(new Position(p.getX(), p.getY()));
            }
        }
        if (res.isEmpty()) {
            res.add(new Position(0, 0));
        }
        return res;
    }

    private static void nextMission(Run run) {
        if (run.state != State.FIGHT) {
            return;
        }
        Field field = field(run);
        if (field == null) {
            return;
        }
        int[] mobs = MOBS.get()[run.stage];
        List<Position> spots = spots(field);
        run.missionMobs.clear();
        if (run.mission % 2 == 0) {
            for (int i = 0; i < NORMAL_SCOUTS + NORMAL_BRUTES; i++) {
                Position p = spots.get(Util.getRandom(spots.size() - 1));
                int id = i < NORMAL_SCOUTS ? mobs[0] : mobs[1];
                Mob mob = field.spawnMob(id, p.getX() + Util.getRandom(-80, 80), p.getY(), false, -1);
                if (mob != null) {
                    run.missionMobs.add(mob);
                }
            }
        } else {
            Mob brute = MobData.getMobDeepCopyById(mobs[1]);
            long hp = brute == null ? 0 : brute.getMaxHp() * BOSS_HP_FACTOR;
            Position p = spots.get(Util.getRandom(spots.size() - 1));
            Mob boss = field.spawnMob(mobs[2 + run.mission / 2], p.getX(), p.getY(), false, hp);
            if (boss != null) {
                run.missionMobs.add(boss);
            }
        }
        current(run).write(ContentPacket.championRaidField(1, run.stage + 1, run.score, run.mission));
    }

    private static void onMobDeath(Run run, Mob mob) {
        if (run.state != State.FIGHT || !run.missionMobs.remove(mob) || !run.missionMobs.isEmpty()) {
            return;
        }
        boolean boss = run.mission % 2 == 1;
        run.score += (boss ? BOSS_POINTS : NORMAL_POINTS) * (run.stage + 1);
        run.mission++;
        Char c = current(run);
        c.write(ContentPacket.championRaidField(1, run.stage + 1, run.score, run.mission));
        if (run.mission < MISSIONS) {
            EventManager.addEvent(() -> nextMission(run), 2000);
            return;
        }
        // stage cleared: the clock stops until the next stage
        settleTime(run);
        run.state = State.CLEARED;
        c.write(ContentPacket.championRaidPause(PAUSE_MS));
        c.write(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz("Effect/ChampionRaid.img/clear", 0)));
        if (run.stage + 1 >= STAGES.length) {
            notice(c, "Draco Isle is conquered!");
            EventManager.addEvent(() -> end(run, true), 4000);
        } else {
            notice(c, String.format("Stage %d cleared! Score: %,d. Take the portal in the middle to the next stage.",
                    run.stage + 1, run.score));
        }
    }

    /** Portal next_championRai. */
    public static void nextStage(Char chr) {
        Run run = runsByClient.get(chr.getClient());
        if (run == null || run.state != State.CLEARED || run.stage + 1 >= STAGES.length) {
            chr.chatMessage("The portal opens once every mission of this stage is done.");
            chr.dispose();
            return;
        }
        Field next = run.instance.getField(STAGES[run.stage + 1]);
        if (next == null) {
            chr.dispose();
            return;
        }
        current(run).warp(next);
        beginStage(run, run.stage + 1);
        run.activeSince = System.currentTimeMillis();
        run.state = State.FIGHT;
        nextMission(run);
    }

    // ------------------------------------------------------------------------------------------------ time and tags

    private static void settleTime(Run run) {
        long now = System.currentTimeMillis();
        Member a = run.active;
        if (a != null && run.state == State.FIGHT) {
            a.remainingMs = (int) Math.max(0, a.remainingMs - (now - run.activeSince));
        }
        run.activeSince = now;
    }

    private static void tick(Run run) {
        try {
            if (run.state == State.ENDED) {
                return;
            }
            Char c = current(run);
            if (c.getClient() != run.client || c.getField() == null || c.getInstance() != run.instance) {
                end(run, false);
                return;
            }
            if (run.state != State.FIGHT) {
                return;
            }
            settleTime(run);
            Member a = run.active;
            if (a == null || a.remainingMs > 0) {
                run.outSince = 0;
                return;
            }
            Member next = run.mostTime(a);
            if (next == null) {
                notice(c, "Every Champion's time is used up.");
                end(run, true);
                return;
            }
            // the client tags by itself when the time runs out; if it doesn't, the server does
            long now = System.currentTimeMillis();
            if (run.outSince == 0) {
                run.outSince = now;
            } else if (now - run.outSince > 3000) {
                tag(run, next, true);
            }
        } catch (Exception e) {
            log.error("Champion Raid tick failed", e);
        }
    }

    /** UNION_CHAMPION_RAID_REQUEST 1154 */
    public static void handleRequest(Char chr, InPacket in) {
        Run run = runsByClient.get(chr.getClient());
        int type = in.decodeInt();
        if (run == null) {
            chr.dispose();
            return;
        }
        if (type == 1) {
            int slot = in.decodeInt();
            boolean forced = in.getUnreadAmount() > 0 && in.decodeByte() != 0;
            Member m = run.bySlot(slot);
            if (m == null || m == run.active || run.state != State.FIGHT) {
                chr.dispose();
                return;
            }
            settleTime(run);
            if (m.remainingMs <= 0) {
                chr.write(ContentPacket.championRaidTimeOver(slot));
                chr.dispose();
                return;
            }
            if (!forced && System.currentTimeMillis() - run.lastTag < TAG_COOLDOWN_MS) {
                chr.dispose();
                return;
            }
            tag(run, m, forced);
        } else {
            chr.dispose();
        }
    }

    private static synchronized void tag(Run run, Member to, boolean forced) {
        if (run.state == State.ENDED || to == run.active) {
            return;
        }
        settleTime(run);
        Member from = run.active;
        if (from != null && from.remainingMs <= 0) {
            from.chr.write(ContentPacket.championRaidTimeOver(from.slot));
        }
        swapTo(run, to);
        Char c = to.chr;
        c.write(ContentPacket.championRaidSwap(to.slot, ++run.swaps));
        c.write(ContentPacket.championRaidSwapDone());
        c.heal(c.getMaxHP(), false, true);
        c.healMP(c.getMaxMP());
        c.dispose();
        run.lastTag = System.currentTimeMillis();
        run.activeSince = run.lastTag;
        run.outSince = 0;
    }

    /** Moves the session from the active character to another squad member (no packets about the swap itself). */
    private static void swapTo(Run run, Member to) {
        Char from = current(run);
        Char next = to.chr;
        if (from == next) {
            run.active = to;
            return;
        }
        Field field = from.getField();
        Position pos = from.getPosition() == null ? new Position(0, 0) : from.getPosition().deepCopy();
        from.removeLifesFromCurrentField();
        from.getTemporaryStatManager().removeAllStats();
        if (from != run.owner && from.getJobHandler() != null) {
            from.getJobHandler().stopTimers();
        }

        User user = run.owner.getUser();
        Account acc = run.owner.getAccount();
        next.setClient(run.client);
        next.setUser(user);
        next.setAccount(acc);
        if (next.getJobHandler() == null) {
            next.setJobHandler(JobManager.getJobById(next.getJob(), next));
        }
        if (field != null) {
            field.getChars().remove(from);
            next.setField(field);
            next.setPosition(pos);
            if (!field.getChars().contains(next)) {
                field.getChars().add(next);
            }
            for (Map.Entry<Life, Char> e : new HashMap<>(field.getLifeToControllers()).entrySet()) {
                if (e.getValue() == from) {
                    field.putLifeController(e.getKey(), next);
                }
            }
        }
        run.instance.getChars().remove(from);
        if (!run.instance.getChars().contains(next)) {
            run.instance.getChars().add(next);
        }
        next.setInstance(run.instance);
        if (from != run.owner) {
            from.setInstance(null);
        }
        run.client.setChr(next);
        if (user != null) {
            user.setCurrentChr(next);
        }
        if (acc != null) {
            acc.setCurrentChr(next);
        }
        run.active = to;
    }

    // ------------------------------------------------------------------------------------------------ end

    /** Back to the owner (server side and on the client), score into coins, out to the exit. */
    private static synchronized void end(Run run, boolean warp) {
        if (run.state == State.ENDED) {
            return;
        }
        settleTime(run);
        run.state = State.ENDED;
        if (run.tick != null) {
            run.tick.cancel(false);
        }
        if (run.setup != null) {
            run.setup.cancel(false);
        }
        runsByOwner.remove(run.owner.getId());
        runsByClient.remove(run.client);
        Char owner = run.owner;
        try {
            Char cur = current(run);
            if (cur != owner) {
                Member ownerMember = null;
                for (Member m : run.members) {
                    if (m.chr == owner) {
                        ownerMember = m;
                    }
                }
                swapTo(run, ownerMember != null ? ownerMember : new Member(-1, owner));
            }
            if (run.started) {
                owner.write(ContentPacket.championRaidSwap(-1, ++run.swaps));
                owner.write(FieldPacket.fieldEffect(FieldEffect.getFieldEffectFromWz("Effect/ChampionRaid.img/finish", 0)));
            }
            for (Member m : run.members) {
                if (m.chr != owner) {
                    m.chr.getAvatarData().getCharacterStat().setPosMap(m.posMap);
                    m.chr.setInstance(null);
                    m.chr.setClient(null);
                    if (m.chr.getJobHandler() != null) {
                        m.chr.getJobHandler().stopTimers();
                    }
                }
            }
            if (run.started) {
                reward(owner, run.score);
            }
        } catch (Exception e) {
            log.error("Champion Raid end failed for " + owner.getName(), e);
        }
        if (warp && owner.getInstance() == run.instance && owner.getClient() != null) {
            owner.heal(owner.getMaxHP(), false, true);
            owner.getScriptManager().warpInstanceOut(EXIT, 0);
        }
    }

    /** Before a logout: the session's character goes back to the owner so the owner is the one saved and removed. */
    public static Char onDisconnect(Char chr) {
        if (chr == null) {
            return null;
        }
        Run run = runsByClient.get(chr.getClient());
        if (run == null) {
            return chr;
        }
        end(run, false);
        return run.owner;
    }

    // ------------------------------------------------------------------------------------------------ coins

    private static String month() {
        var now = ResetClock.now();
        return String.format("%04d-%02d", now.getYear(), now.getMonthValue());
    }

    private static int[] record(Account acc) {
        int[] res = {0, 0};
        for (Map<String, Object> row : DatabaseManager.queryRows("SELECT month AS m, best AS b, coins AS c FROM "
                + "champion_raid WHERE accid = ?", acc.getId())) {
            if (month().equals(row.get("m"))) {
                res[0] = ((Number) row.get("b")).intValue();
                res[1] = ((Number) row.get("c")).intValue();
            }
        }
        return res;
    }

    private static void saveRecord(Account acc, int best, int coins) {
        DatabaseManager.executeQuery("INSERT INTO champion_raid (accid, month, best, coins) VALUES (?, ?, ?, ?) ON "
                + "DUPLICATE KEY UPDATE month = VALUES(month), best = VALUES(best), coins = VALUES(coins)",
                acc.getId(), month(), best, coins);
    }

    public static int getCoins(Account acc) {
        return acc == null ? 0 : record(acc)[1];
    }

    /** Champion Coins on the client (quest-ex 505005 point). */
    public static void sendCoins(Char chr) {
        if (chr == null || chr.getAccount() == null) {
            return;
        }
        Quest q = chr.getQuestManager().getOrCreateQuestById(COIN_QUEST);
        q.setProperty("point", getCoins(chr.getAccount()));
        chr.write(WvsContext.message(MessagePacket.questRecordExMessage(q)));
    }

    /** Only a better score than this month's best adds coins (the difference). */
    private static void reward(Char owner, int score) {
        Account acc = owner.getAccount();
        int[] rec = record(acc);
        int gain = Math.max(0, score - rec[0]);
        saveRecord(acc, Math.max(rec[0], score), rec[1] + gain);
        sendCoins(owner);
        owner.chatMessage(ChatType.SystemNotice, gain > 0
                ? String.format("[Champion Raid] Score %,d: %,d Champion Coins (best this month: %,d).", score, gain,
                Math.max(rec[0], score))
                : String.format("[Champion Raid] Score %,d. Beat this month's best of %,d for more Champion Coins.",
                score, rec[0]));
    }

    public static boolean spendCoins(Char chr, int amount) {
        Account acc = chr.getAccount();
        int[] rec = record(acc);
        if (amount <= 0 || rec[1] < amount) {
            return false;
        }
        saveRecord(acc, rec[0], rec[1] - amount);
        sendCoins(chr);
        return true;
    }

    private static void notice(Char chr, String msg) {
        chr.chatMessage(ChatType.SystemNotice, "[Champion Raid] " + msg);
    }

    private static final class Helper extends InstanceHelper {
        private final Run run;

        Helper(Run run) {
            this.run = run;
        }

        @Override
        protected void startStage(int stageNumber) {
        }

        @Override
        public void onMobDeath(Field field, Mob mob) {
            ChampionRaid.onMobDeath(run, mob);
        }

        @Override
        public void onChrDeath(Char chr) {
            if (run.state != State.FIGHT || chr != current(run)) {
                return;
            }
            Member a = run.active;
            chr.heal(chr.getMaxHP(), false, true);
            if (a != null) {
                a.remainingMs = 0;
                chr.write(ContentPacket.championRaidTimeOver(a.slot));
            }
            Member next = run.mostTime(a);
            if (next == null) {
                end(run, true);
            } else {
                tag(run, next, true);
            }
        }

        @Override
        public void onInstanceCleared(Instance instance) {
            end(run, false);
        }
    }
}
