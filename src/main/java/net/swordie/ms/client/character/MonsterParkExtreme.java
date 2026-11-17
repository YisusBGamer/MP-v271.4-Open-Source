package net.swordie.ms.client.character;

import net.swordie.ms.client.party.Party;
import net.swordie.ms.connection.packet.ContentPacket;
import net.swordie.ms.enums.ChatType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.ResetClock;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;
import net.swordie.orm.connection.HikariCPDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * Monster Park Extreme (hub portal in03 opens the Extreme window; map 951000300, fieldType 310; maplestorywiki):
 * Lv. 260+, a party of 1-4, one clear per character and two per world each week (reset Thursday). Hunt 50 each of
 * 4 random Mysterious monsters (a Mysterious Jackalope takes 15 off each), then hold the NPC chat key on the altar
 * to summon Extreme Bigfoot. He stays 30 s, then 60, 90, 120; if he gets away the hunt starts over; the fifth time
 * he stays until defeated. Reward: the level's EXP (no Sunday bonus) and the day-of-the-week box.
 */
public class MonsterParkExtreme {
    private static final Logger log = LogManager.getLogger(MonsterParkExtreme.class);

    public static final int FIELD = 951000300;
    public static final int EXIT = 951000400;
    public static final int MIN_LEVEL = 260;
    public static final int MAX_PARTY = 4;
    public static final int TIME_LIMIT_SECONDS = 10 * 60;
    public static final int CLEARS_PER_WORLD = 2;
    public static final int WINDOW_QUEST = 502345;
    public static final String TABLE = "monster_park_extreme_clears";
    public static final short RUN_SCRIPT_WINDOW = 97;

    private static final int[] MOBS = {9801500, 9801501, 9801502, 9801503, 9801504, 9801505, 9801506, 9801507};
    private static final int JACKALOPE = 9801508;
    private static final int BIGFOOT = 9801509;
    private static final int KILLS = 50;
    private static final int JACKALOPE_KILLS = 15;
    private static final int HUNT_TYPES = 4;
    private static final int MAX_ALIVE = 40;
    private static final int HOLD_MS = 3000;
    // centre of the altar platform: between the map's four altaron0-3 braziers (x 1372-1924), on its foothold (y -1761)
    private static final int ALTAR_X = 1648, ALTAR_Y = -1761;
    private static final int[] ALTAR_AREA = {-100, -150, 100, 20};
    private static final int[] STAY_SECONDS = {30, 60, 90, 120};
    private static final int[][] SPOTS = {{992, -1843}, {1488, -1835}, {1978, -1831}, {2375, -1830},
            {-1031, -3042}, {-1477, -2638}, {-551, -2635}, {3546, -1691}, {4207, -2134}, {4611, -1695}};

    /** EXP by level, 260-294 (maplestorywiki); 295+ use 294's. */
    private static final long[] EXP = {
            265_200_000_000L, 266_220_000_000L, 267_240_000_000L, 268_260_000_000L, 269_280_000_000L,
            270_300_000_000L, 271_320_000_000L, 272_340_000_000L, 273_360_000_000L, 274_380_000_000L,
            275_400_000_000L, 276_420_000_000L, 277_440_000_000L, 278_460_000_000L, 279_480_000_000L,
            740_271_120_000L, 749_602_080_000L, 760_157_550_000L, 769_586_940_000L, 780_227_580_000L,
            876_186_885_000L, 888_143_070_000L, 898_815_840_000L, 910_862_550_000L, 921_640_890_000L,
            1_036_056_075_000L, 1_048_127_520_000L, 1_061_756_505_000L, 1_075_469_130_000L, 1_087_699_695_000L,
            1_222_296_090_000L, 1_235_988_060_000L, 1_251_492_060_000L, 1_267_089_135_000L, 1_280_990_205_000L};

    private static final class Run {
        final Map<Integer, Integer> hunt = new LinkedHashMap<>();
        volatile int altar;
        volatile int appearances;
        volatile long bossHp;
        volatile Mob boss;
        volatile boolean done;
        ScheduledFuture<?> waves;
        ScheduledFuture<?> escape;
    }

    private static final Map<Instance, Run> runs = new ConcurrentHashMap<>();

    public static long getClearExp(int level) {
        return EXP[Math.max(0, Math.min(EXP.length - 1, level - MIN_LEVEL))];
    }

    /** The week a moment belongs to: the date of the Thursday it starts on. */
    public static String week(ZonedDateTime t) {
        return t.with(TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY)).toLocalDate().toString();
    }

    public static String currentWeek() {
        return week(ResetClock.now());
    }

    private static List<String> clearedNames(int accountId) {
        List<String> res = new ArrayList<>();
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT char_name FROM " + TABLE
                     + " WHERE week = ? AND account_id = ? ORDER BY cleared_at")) {
            ps.setString(1, currentWeek());
            ps.setInt(2, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    res.add(rs.getString(1));
                }
            }
        } catch (Exception e) {
            log.error("Could not read Monster Park Extreme clears", e);
        }
        return res;
    }

    private static boolean hasCleared(Char chr) {
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT 1 FROM " + TABLE + " WHERE week = ? AND char_id = ?")) {
            ps.setString(1, currentWeek());
            ps.setInt(2, chr.getId());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            log.error("Could not read a Monster Park Extreme clear", e);
            return false;
        }
    }

    public static boolean hasClearedThisWeek(Char chr) {
        return hasCleared(chr);
    }

    static void recordClear(Char chr) {
        if (chr.getAccount() == null) {
            return;
        }
        try (Connection con = HikariCPDataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT IGNORE INTO " + TABLE
                     + " (week, char_id, account_id, char_name) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, currentWeek());
            ps.setInt(2, chr.getId());
            ps.setInt(3, chr.getAccount().getId());
            ps.setString(4, chr.getName());
            ps.executeUpdate();
            // Contributed by Arixal (from GitLab).
            con.commit();
        } catch (Exception e) {
            log.error("Could not save a Monster Park Extreme clear", e);
        }
    }

    /** The window's clear list: quest-ex 502345 name1/name2, this week's clears in the world. */
    public static void updateWindowRecord(Char chr) {
        if (chr.getAccount() == null) {
            return;
        }
        chr.getScriptManager().createQuestWithQRValue(WINDOW_QUEST,
                windowRecord(clearedNames(chr.getAccount().getId()), unlimited()));
    }

    // Contributed by Arixal (from GitLab).
    /** The record's value. With the limits off the window lists nothing, as no clear counts against anything then. */
    static String windowRecord(List<String> names, boolean unlimited) {
        if (unlimited) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(CLEARS_PER_WORLD, names.size()); i++) {
            sb.append(i == 0 ? "" : ";").append("name").append(i + 1).append('=').append(names.get(i));
        }
        return sb.toString();
    }

    /** Hub portal in03. */
    public static void openWindow(Char chr) {
        updateWindowRecord(chr);
        chr.write(ContentPacket.monsterParkExtremeWindow());
    }

    /** USER_RUN_SCRIPT 97 from the Extreme window: int field, int button (2 Enter, 3 Auto-Matching). */
    public static void handleWindow(Char chr, int fieldId, int button) {
        if (chr.getFieldID() != fieldId || chr.getFieldID() != MonsterPark.HUB || chr.getInstance() != null) {
            return;
        }
        if (button == 3) {
            chr.write(ContentPacket.monsterParkResult(0, "Auto-matching isn't available. Gather a party of up to "
                    + MAX_PARTY + " and press Enter."));
            return;
        }
        if (button != 2) {
            return;
        }
        String block = getEntryBlock(chr);
        if (block != null) {
            chr.write(ContentPacket.monsterParkResult(0, block));
            return;
        }
        chr.write(ContentPacket.monsterParkResult(1, ""));
        enter(chr);
    }

    /** setting monster_park_extreme_unlimited: no weekly limits */
    public static boolean unlimited() {
        return net.swordie.ms.constants.ServerFeatures.on(
                net.swordie.ms.constants.ServerFeatures.Feature.MONSTER_PARK_EXTREME_UNLIMITED);
    }

    /** Why this character (as leader) can't take its party in, or null. */
    public static String getEntryBlock(Char chr) {
        Party party = chr.getParty();
        List<Char> members = party == null ? List.of(chr) : party.getPartyMembersInField(chr);
        if (party != null && party.getPartyLeaderID() != chr.getId()) {
            return "Please have your party leader enter.";
        }
        if (members.size() > MAX_PARTY) {
            return "Monster Park Extreme takes a party of " + MAX_PARTY + " at most.";
        }
        for (Char c : members) {
            String who = c == chr ? "" : c.getName() + ": ";
            if (c.getLevel() < MIN_LEVEL) {
                return who + "You must be Level " + MIN_LEVEL + " or higher to enter Monster Park Extreme.";
            }
            if (unlimited()) {
                continue;
            }
            if (hasCleared(c)) {
                return who + "This character has already cleared Monster Park Extreme this week.";
            }
            if (c.getAccount() != null && clearedNames(c.getAccount().getId()).size() >= CLEARS_PER_WORLD) {
                return who + String.format("%d characters within the world have cleared all of Monster Park "
                        + "Extreme this week.", CLEARS_PER_WORLD);
            }
        }
        return null;
    }

    public static boolean enter(Char chr) {
        String block = getEntryBlock(chr);
        if (block != null) {
            chr.chatMessage(block);
            return false;
        }
        var sm = chr.getScriptManager();
        sm.warpInstanceIn(FIELD, 0, true);
        Instance inst = chr.getInstance();
        if (inst == null) {
            return false;
        }
        sm.setInstanceTime(TIME_LIMIT_SECONDS, EXIT);
        Run run = new Run();
        List<Integer> pool = new ArrayList<>();
        for (int id : MOBS) {
            pool.add(id);
        }
        Collections.shuffle(pool);
        for (int i = 0; i < HUNT_TYPES; i++) {
            run.hunt.put(pool.get(i), 0);
        }
        run.altar = 1;
        runs.put(inst, run);
        Field field = inst.getField(FIELD);
        if (field != null) {
            field.broadcastPacket(ContentPacket.monsterParkExtremeStart(TIME_LIMIT_SECONDS, HOLD_MS, ALTAR_X, ALTAR_Y,
                    ALTAR_AREA[0], ALTAR_AREA[1], ALTAR_AREA[2], ALTAR_AREA[3]));
            sendHunt(field, run);
            field.broadcastPacket(ContentPacket.monsterParkExtremeAltar(run.altar, 0));
        }
        run.waves = EventManager.addFixedRateEvent(() -> wave(inst, run), 3000, 12000);
        return true;
    }

    /** The awake altar's value (what holding the key on it sends back), or 0 while it sleeps or Bigfoot is out. */
    public static int getAwakeAltar(Instance inst) {
        Run run = inst == null ? null : runs.get(inst);
        if (run == null || run.done || run.boss != null || !huntDone(run)) {
            return 0;
        }
        return run.altar;
    }

    /** Where the altar stands: x, y. */
    public static int[] getAltarSpot() {
        return new int[]{ALTAR_X, ALTAR_Y};
    }

    public static int getHoldMs() {
        return HOLD_MS;
    }

    private static void sendHunt(Field field, Run run) {
        field.broadcastPacket(ContentPacket.monsterParkExtremeReset());
        synchronized (run.hunt) {
            for (Map.Entry<Integer, Integer> e : run.hunt.entrySet()) {
                field.broadcastPacket(ContentPacket.monsterParkExtremeHunt(e.getKey(), e.getValue(), KILLS));
            }
        }
    }

    private static boolean huntDone(Run run) {
        synchronized (run.hunt) {
            return run.hunt.values().stream().allMatch(v -> v >= KILLS);
        }
    }

    private static void wave(Instance inst, Run run) {
        Field field = inst.getField(FIELD);
        if (run.done || field == null || inst.getChars().isEmpty()) {
            stop(inst, run);
            return;
        }
        if (run.boss != null || huntDone(run) || field.getMobs().size() >= MAX_ALIVE) {
            return;
        }
        List<Integer> types;
        synchronized (run.hunt) {
            types = new ArrayList<>(run.hunt.keySet());
        }
        for (int i = 0; i < 8; i++) {
            int[] spot = SPOTS[Util.getRandom(SPOTS.length - 1)];
            field.spawnMob(types.get(Util.getRandom(types.size() - 1)), spot[0] + Util.getRandom(-120, 120), spot[1],
                    false, -1);
        }
        if (Util.succeedProp(15)) {
            int[] spot = SPOTS[Util.getRandom(SPOTS.length - 1)];
            field.spawnMob(JACKALOPE, spot[0], spot[1], false, -1);
        }
    }

    private static void stop(Instance inst, Run run) {
        if (run.waves != null) {
            run.waves.cancel(false);
        }
        if (run.escape != null) {
            run.escape.cancel(false);
        }
        runs.remove(inst);
    }

    /** Field.onMobDeath */
    public static void onMobDeath(Field field, Mob mob) {
        if (field.getId() != FIELD || field.getInstance() == null) {
            return;
        }
        Instance inst = field.getInstance();
        Run run = runs.get(inst);
        if (run == null || run.done) {
            return;
        }
        int id = mob.getTemplateId();
        if (id == BIGFOOT) {
            if (mob == run.boss) {
                finish(inst, run, field);
            }
            return;
        }
        if (run.boss != null || huntDone(run)) {
            return;
        }
        synchronized (run.hunt) {
            if (id == JACKALOPE) {
                run.hunt.replaceAll((k, v) -> Math.min(KILLS, v + JACKALOPE_KILLS));
                for (Map.Entry<Integer, Integer> e : run.hunt.entrySet()) {
                    field.broadcastPacket(ContentPacket.monsterParkExtremeHunt(e.getKey(), e.getValue(), KILLS));
                }
            } else if (run.hunt.containsKey(id) && run.hunt.get(id) < KILLS) {
                int n = run.hunt.merge(id, 1, Integer::sum);
                field.broadcastPacket(ContentPacket.monsterParkExtremeHunt(id, n, KILLS));
            } else {
                return;
            }
        }
        if (huntDone(run)) {
            field.broadcastPacket(ContentPacket.monsterParkExtremeAltar(run.altar, 1));
            notice(field, "The altar has awakened! Hold the NPC Chat key on the altar to summon Extreme Bigfoot.");
        }
    }

    /** MONSTER_PARK_EXTREME_ALTAR_REQUEST: someone held the key on the enabled altar. */
    public static void onAltar(Char chr, int value) {
        Field field = chr.getField();
        Instance inst = chr.getInstance();
        Run run = inst == null ? null : runs.get(inst);
        if (field == null || field.getId() != FIELD || run == null || run.done) {
            return;
        }
        synchronized (run) {
            if (value != run.altar || run.boss != null || !huntDone(run)) {
                return;
            }
            field.broadcastPacket(ContentPacket.monsterParkExtremeAltar(run.altar, 2));
            for (Mob m : new ArrayList<>(field.getMobs())) {
                m.disappear();
            }
            int[] spot = SPOTS[Util.getRandom(SPOTS.length - 1)];
            Mob boss = field.spawnMob(BIGFOOT, spot[0], spot[1], false, -1);
            if (boss == null) {
                return;
            }
            if (run.bossHp > 0 && run.bossHp < boss.getMaxHp()) {
                boss.setHp(run.bossHp);
            }
            run.boss = boss;
            int stay = run.appearances < STAY_SECONDS.length ? STAY_SECONDS[run.appearances] : 0;
            run.appearances++;
            field.broadcastPacket(ContentPacket.monsterParkExtremeBoss(BIGFOOT, run.appearances,
                    System.currentTimeMillis() + stay * 1000L, hpPerMille(boss), stay * 1000));
            notice(field, stay > 0 ? String.format("Extreme Bigfoot has appeared somewhere! He leaves in %d seconds.",
                    stay) : "Extreme Bigfoot has appeared somewhere, and this time he won't run!");
            if (stay > 0) {
                run.escape = EventManager.addEvent(() -> escape(inst, run, boss), stay * 1000L);
            }
        }
    }

    private static int hpPerMille(Mob mob) {
        return mob.getMaxHp() <= 0 ? 0 : (int) Math.max(0, Math.min(1000, mob.getHp() * 1000 / mob.getMaxHp()));
    }

    private static void escape(Instance inst, Run run, Mob boss) {
        Field field = inst.getField(FIELD);
        synchronized (run) {
            if (run.done || run.boss != boss || field == null) {
                return;
            }
            run.bossHp = boss.getHp();
            run.boss = null;
            boss.disappear();
            synchronized (run.hunt) {
                run.hunt.replaceAll((k, v) -> 0);
            }
            run.altar++;
            field.broadcastPacket(ContentPacket.monsterParkExtremeBoss(BIGFOOT, run.appearances, 0, 0, 0));
            sendHunt(field, run);
            field.broadcastPacket(ContentPacket.monsterParkExtremeAltar(run.altar, 0));
            notice(field, "Extreme Bigfoot got away! Hunt the monsters again to reawaken the altar.");
        }
    }

    private static void finish(Instance inst, Run run, Field field) {
        run.done = true;
        stop(inst, run);
        field.broadcastPacket(ContentPacket.monsterParkExtremeBoss(BIGFOOT, run.appearances, 0, 0, 0));
        int box = MonsterPark.getRewardByDay();
        for (Char chr : inst.getChars()) {
            if (chr.getFieldID() != FIELD) {
                continue;
            }
            long exp = getClearExp(chr.getLevel());
            chr.addExp(exp);
            if (chr.canHold(box, 1)) {
                chr.getScriptManager().giveItem(box);
            }
            recordClear(chr);
            chr.chatMessage(ChatType.SystemNotice, String.format(
                    "Monster Park Extreme cleared! You gained %,d EXP and a day-of-the-week box.", exp));
        }
        EventManager.addEvent(() -> {
            for (Char chr : inst.getChars()) {
                chr.getScriptManager().warpInstanceOut(EXIT, 0);
            }
        }, 5000);
    }

    private static void notice(Field field, String msg) {
        for (Char chr : field.getChars()) {
            chr.chatMessage(ChatType.SystemNotice, "[Monster Park Extreme] " + msg);
        }
    }
}
