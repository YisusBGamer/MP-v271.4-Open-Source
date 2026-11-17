package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.enums.WeatherEffNoticeType;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.handlers.user.UserStatHandler;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobPatternDriver;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.Portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Jupiter: Normal, Hard; level 295, 30 minutes, 1-3 players. One map (410027100 / 410028100) with three areas: up
 * (ptLuppiterA, Jupiter's zone, ZoneType 0), down (ptLuppiterB, the Alchemical Entity's zone, ZoneType 1) and
 * middle (ptLuppiterC, the merged arena). One HP pool: the invisible holder (+0) has the HP, every visible form is
 * HP-linked to it.
 * <p>
 * The fight alternates (Mob.wz BossFieldSetInfo.img/bossLuppiter):
 * <ul>
 * <li>Split: Jupiter above and the Alchemical Entity below at the same time, 150 / 120 / 90 s by the phase at its
 * start. Command every 50 / 40 / 30 s, the third one at the merge: Magic Sync level 0 lowers everyone's Disruption
 * by 10, level 1/2/3 raises it by 60/80/100. Aether Warp (80004049, temporary skill bar) swaps zone when the other
 * side has fewer than 2 players; cooldown 15/20/30 s for 1/2/3 players.</li>
 * <li>Merge: everyone to the middle, one fused form for 100 s chosen by the Magic Sync gauge: Jupiter side
 * Auspicious, centred Balanced, Entity side Ruinous. In Hard every third merge is never Balanced.</li>
 * </ul>
 * Magic Sync gauge 0-1000 (500): hits on the Jupiter side lower it, on the Entity side raise it; every 3 s it
 * drifts 25 further when outside 400-600; back to 500 at each merge. Disruption (user context DeathCount, 0-1000)
 * replaces the death count: +150 per death while split, +200 merged, out of the fight at 1000. The values reach the
 * client's Jupiter window through Out 971.
 * <p>
 * Attacks are the client's pattern system (Mob.wz info/patternSys, categories 1095-1111). Split: Jupiter (+1) and its
 * helper (+2, 1096) with the small objects (+15 / +16) above, the Entity (+4) and its helper (+5, 1099) below. Merged:
 * the form and its helpers (+7 / +8, +10, +12). The Command helper (+3, 1097) and the merge/split controller (+14,
 * 1108) stay for the whole fight; their patterns are queued only by the server, at each Command and each merge /
 * split, so their art plays with the server's result. A hit feeds the gauge by the hitting mob's last run (the
 * graph's table per category / pattern / sub-skill; the k-th hit of a run is taken as sub-skill k).
 */
public class JupiterInstanceHelper extends StagedBossHelper {

    private static final String[] ZONE_PORTALS = {"ptLuppiterA", "ptLuppiterB"};
    private static final String MIDDLE_PORTAL = "ptLuppiterC";
    private static final Position UP = new Position(0, -2390);
    private static final Position DOWN = new Position(0, 2410);
    private static final Position MIDDLE = new Position(0, 10);
    private static final boolean SMALL_OBJECTS =
            net.swordie.ms.ServerSettings.getBoolean("test.jupiterSmallObjects", true);
    // offsets from the mode's base
    private static final int JUPITER = 1, JUPITER_HELPER = 2, COMMAND = 3, ENTITY = 4, ENTITY_HELPER = 5;
    private static final int CONTROLLER = 14, SMALL_A = 15, SMALL_B = 16;
    private static final int[] FORM = {0, 6, 9, 11};
    private static final int[][] FORM_HELPERS = {{}, {7, 8}, {10}, {12}};
    private static final int CAT_COMMAND = 1097, CAT_CONTROLLER = 1108;
    private static final long RUN_HIT_WINDOW_MS = 15_000;
    private static final long PATTERN_HIT_THROTTLE_MS = 500;
    private static final int HIT_LOG_MAX = 30;
    // MagicSyncGauge per pattern hit (graph 3 H2): {category, pattern, sub-skill, delta}
    private static final int[][] GAUGE_HITS = {
            {1095, 0, 0, -60}, {1095, 1, 0, -50}, {1095, 2, 0, -100}, {1095, 3, 0, -80}, {1095, 3, 1, -110},
            {1095, 5, 0, -90}, {1095, 6, 0, -45}, {1095, 7, 0, -55}, {1096, 0, 0, -35}, {1096, 1, 0, -70},
            {1096, 2, 0, -30}, {1096, 2, 1, -35}, {1111, 0, 0, -30},
            {1098, 0, 0, 50}, {1098, 0, 1, 50}, {1098, 2, 0, 25}, {1098, 3, 0, 45}, {1098, 4, 0, 45},
            {1098, 5, 0, 45}, {1098, 6, 0, 60}, {1098, 6, 1, 90}, {1098, 6, 2, 100}, {1098, 7, 0, 60},
            {1098, 7, 1, 90}, {1098, 7, 2, 100}, {1098, 8, 0, 30}, {1098, 8, 1, 60}, {1098, 9, 0, 100},
            {1098, 10, 0, 30}, {1098, 11, 0, 25}, {1098, 11, 1, 25}, {1099, 0, 0, 15}, {1099, 1, 0, 20}};
    private static final Map<Integer, Integer> GAUGE_BY_HIT = new HashMap<>();
    private static final Map<Integer, Integer> MAX_SUB = new HashMap<>();

    static {
        for (int[] h : GAUGE_HITS) {
            GAUGE_BY_HIT.put((h[0] * 1000 + h[1]) * 10 + h[2], h[3]);
            MAX_SUB.merge(h[0] * 1000 + h[1], h[2], Math::max);
        }
    }

    private static final long[] COMMAND_MS = {50_000, 40_000, 30_000}; // by phase; a split lasts three Commands
    private static final long MERGE_MS = 100_000;
    private static final long WARNING_MS = 7_500;
    private static final long DRIFT_MS = 3_000;
    private static final long DRIFT_DELAY_MS = 5_000;
    private static final long BALANCE_TICK_MS = 20_000; // inferred
    private static final long MOVE_MS = 1_500;
    private static final long HIT_THROTTLE_MS = 1_000;
    private static final int[] COMMAND_FAIL = {0, 60, 80, 100};
    private static final int COMMAND_SUCCESS = -10;
    private static final int DEATH_SPLIT = 150;
    private static final int DEATH_MERGED = 200;
    private static final int DISRUPTION_MAX = 1000;
    private static final int HIT_JUPITER_SIDE = -50; // inferred: middle of the client's -30..-110
    private static final int HIT_ENTITY_SIDE = 50; // inferred: middle of the client's +15..+100
    private static final int P3_GAUGE_SHIFT = 100; // inferred: "the sync gauge is altered"
    private static final int AETHER_WARP = 80004049;
    private static final long[] WARP_COOLDOWN_MS = {15_000, 20_000, 30_000};
    private static final int TEMP_BAR = 116; // UITemporarySkillBar set 116 holds only 80004049 (Interact key)
    private static final int OUT_AFTER_MS = 10_000; // dead at full Disruption: sent out if still lying there
    private static final int WEATHER_WARN_MS = 4_000;
    private static final int WEATHER_COMMAND_MS = 3_000;

    // values of the Merged context
    private static final int SPLIT = 0;
    private static final int AUSPICIOUS = 1;
    private static final int BALANCED = 2;
    private static final int RUINOUS = 3;
    private static final String[] FORM_NAMES = {"split", "Auspicious", "Balanced", "Ruinous"};

    private volatile int mode = -1;
    private Stage[] stages;
    private final FieldSetContext ctx = new FieldSetContext()
            .field("Merged", 0, 0, 3).field("MagicSyncGauge", 500, 0, 1000).field("MagicSyncLevel", 0, -3, 3)
            .field("Phase", 1, 1, 3).field("RemainingOrderCount", 0, 0, 10).field("BalanceGauge", 0, 0, 2)
            .field("RollBackDeathCountPercent", 0, 0, 100).field("MergeHistory0", -1, -1, 2)
            .field("MergeHistory1", -1, -1, 2).field("MergeHistory2", -1, -1, 2)
            .field("MergeCountWhenBalanceNotZero", 0, 0, 1000).field("ShowBiasLvGauge", 0, 0, 1)
            .field("MagicSyncLevelAchievement", 0, 0, 1).field("SpecialMergeFlag", 0, 0, 3)
            .field("SplitLuppiterCommandCount", 0, 0, 3).field("CooltimePhase", 1, 1, 3)
            .field("SpecialMergeTypeFlag", 0, 0, 2)
            .user("DeathCount", 0, 0, DISRUPTION_MAX).user("ZoneType", 0, 0, 1).user("BiasLv", 0, -7, 7)
            .user("isSuppressed", 0, 0, 1).user("ExecutionMark", 0, 0, 2).user("TargetOrderZoneType", -1, -1, 9)
            .user("CurOrderZoneType", -1, -1, 9).user("RollBackDeathCount", 0, 0, DISRUPTION_MAX)
            .user("RandomEnter", 0, 0, 1).user("LastExecutionMark", 0, 0, 2).user("LuppiterOrderSuccess", 0, 0, 1)
            .user("CuttingShotHitCount", 0, 0, 1)
            .userAlias("DeathCountKey", "DeathCount"); // the Disruption box and party list read it by this role

    private volatile boolean started;
    private volatile boolean moving;
    private volatile int merged = SPLIT;
    private volatile long stateSince;
    private volatile long stateEnd;
    private volatile long commandMs;
    private volatile int commandsDone;
    private volatile boolean warned;
    private volatile long nextDrift;
    private volatile long nextBalanceTick;
    private volatile int lastForm = -1;
    private int mergeCount;
    private int specialPicks;
    private volatile Mob jupiter;
    private volatile Mob entity;
    private volatile Mob fused;
    private volatile Mob controller;
    private volatile Mob commander;
    private final List<Mob> splitMobs = new ArrayList<>();
    private final List<Mob> mergedMobs = new ArrayList<>();
    private int splitCount;
    private int hitsLogged;
    // per char: {mob object id, run start, hits counted in that run, last counted hit}
    private final Map<Integer, long[]> runHits = new ConcurrentHashMap<>();
    private final Map<Integer, Long> warpReadyAt = new ConcurrentHashMap<>();
    private final Map<Integer, Long> lastSyncHit = new ConcurrentHashMap<>();

    private int mode() {
        if (mode < 0) {
            Object prop = getInstance() == null ? null : getInstance().getProperty(BossConstants.JUPITER_MODE_KEY);
            int m = prop instanceof Number n ? n.intValue() : -1;
            if (m < 0 && getInstance() != null) {
                m = getInstance().getEnterFieldId() >= BossConstants.JUPITER_FIELDS[1][0] ? 1 : 0;
            }
            mode = Math.max(0, Math.min(1, m));
        }
        return mode;
    }

    private boolean hard() {
        return mode() == 1;
    }

    private int base() {
        return BossConstants.JUPITER_BASE[mode()];
    }

    private long totalHp() {
        long sum = 0;
        for (long hp : BossConstants.JUPITER_HP[mode()]) {
            sum += hp;
        }
        return sum;
    }

    @Override
    protected synchronized Stage[] stages() {
        if (stages == null) {
            stages = new Stage[]{new Stage(BossConstants.JUPITER_FIELDS[mode()][0], base(), totalHp(), new int[0], 0,
                    MIDDLE.getX(), MIDDLE.getY())};
        }
        return stages;
    }

    private boolean inFight(Field field) {
        return field != null && field.getId() == BossConstants.JUPITER_FIELDS[mode()][0];
    }

    @Override
    protected void startMechanics() {
        super.startMechanics(); // the HP holder, the fallback attacks
        later(2_000, () -> {
            synchronized (this) {
                started = true;
                log.info(String.format("[Jupiter] start: %s, map %d, holder %d (%,d HP), controller %d, Command %d",
                        hard() ? "Hard" : "Normal", BossConstants.JUPITER_FIELDS[mode()][0], base(), totalHp(),
                        base() + CONTROLLER, base() + COMMAND));
                startSplit(true);
            }
        });
        every(500, this::tick);
    }

    // ---- split / merge cycle ----

    private synchronized void tick() {
        Field field = getField();
        if (field == null || !started || isBossDead()) {
            return;
        }
        long now = System.currentTimeMillis();
        checkPhase();
        if (!moving) {
            if (merged == SPLIT) {
                splitTick(now);
            } else if (!warned && now >= stateEnd - WARNING_MS) {
                warned = true;
                weather("The power of the gods is about to fail.", WEATHER_WARN_MS);
                notice("Jupiter is distorting the battlefield to his will.");
            } else if (now >= stateEnd) {
                startSplit(false);
            }
        }
        if (ctx.get("BalanceGauge") > 0 && now >= nextBalanceTick) {
            nextBalanceTick = now + BALANCE_TICK_MS;
            balanceTick();
        }
        ctx.flush(field, false);
    }

    private void splitTick(long now) {
        if (now >= nextDrift) {
            nextDrift = now + DRIFT_MS;
            int g = ctx.get("MagicSyncGauge");
            if (g < 400) {
                changeGauge(-25);
            } else if (g > 600) {
                changeGauge(25);
            }
        }
        if (commandsDone < 2 && now >= stateSince + commandMs * (commandsDone + 1)) {
            command();
        }
        if (!warned && now >= stateEnd - WARNING_MS) {
            warned = true;
            int level = ctx.get("MagicSyncLevel");
            if (isSpecialMerge()) {
                ctx.set("SpecialMergeFlag", 2);
                weather(level == 0 ? "I bow to no one." : "I'll be the one to choose your fate.", WEATHER_WARN_MS);
                notice(level == 0 ? "The state of the battlefield is decided, matching the flow."
                        : "You're already about to move.");
            } else {
                weather("Do you have what it takes?", WEATHER_WARN_MS);
                notice("Jupiter is distorting the battlefield to his will.");
            }
        }
        if (now >= stateEnd) {
            merge();
        }
    }

    /** Phase 2 at 79.9 % of the shared HP, phase 3 at 49.9 %; the next split's length follows. */
    private void checkPhase() {
        int permille = holderHpPermille();
        int next = permille <= 499 ? 3 : permille <= 799 ? 2 : 1;
        if (next > ctx.get("Phase")) {
            ctx.set("Phase", next);
            notice("Jupiter enters phase " + next + ".");
            log.info(String.format("[Jupiter] phase %d at %.1f%% (next split %d s, Command every %d s)", next,
                    permille / 10.0, COMMAND_MS[next - 1] * 3 / 1000, COMMAND_MS[next - 1] / 1000));
        }
    }

    /** Hard: every third merge leaves Balanced out. */
    private boolean isSpecialMerge() {
        return hard() && (mergeCount + 1) % 3 == 0;
    }

    private void startSplit(boolean first) {
        Field field = getField();
        if (field == null || isBossDead()) {
            return;
        }
        moving = true;
        int phase = ctx.get("Phase");
        commandMs = COMMAND_MS[phase - 1];
        commandsDone = 0;
        warned = false;
        merged = SPLIT;
        ctx.set("Merged", SPLIT);
        ctx.set("CooltimePhase", phase);
        ctx.set("SplitLuppiterCommandCount", 0);
        ctx.set("ShowBiasLvGauge", 0);
        ctx.set("SpecialMergeFlag", 0);
        ctx.set("SpecialMergeTypeFlag", 0);
        if (!first) {
            balanceAtSplit();
            queuePattern(controller, CAT_CONTROLLER, 1);
        }
        despawn(fused);
        fused = null;
        despawnAll(mergedMobs);
        for (Char chr : new ArrayList<>(field.getChars())) {
            ctx.join(chr);
            moveToZone(chr);
            sendBar(chr);
        }
        later(first ? 0 : MOVE_MS, () -> {
            synchronized (this) {
                if (isBossDead() || getField() != field) {
                    return;
                }
                Mob h = holderMob();
                if (h == null) {
                    log.warn("[Jupiter] no HP holder " + base() + " in " + field.getId() + ": split not spawned");
                    moving = false;
                    return;
                }
                if (first) {
                    controller = spawnHelper(CONTROLLER, MIDDLE, true, null);
                    commander = spawnHelper(COMMAND, MIDDLE, true, null);
                }
                jupiter = spawnLinked(base() + JUPITER, UP, h);
                entity = spawnLinked(base() + ENTITY, DOWN, h);
                splitMobs.add(jupiter);
                splitMobs.add(entity);
                spawnHelper(JUPITER_HELPER, UP, false, splitMobs);
                spawnHelper(ENTITY_HELPER, DOWN, false, splitMobs);
                if (SMALL_OBJECTS) {
                    // inferred: where and when the small objects stand is not in the data
                    spawnHelper(SMALL_A, new Position(-450, UP.getY()), false, splitMobs);
                    spawnHelper(SMALL_B, new Position(450, UP.getY()), false, splitMobs);
                }
                long now = System.currentTimeMillis();
                stateSince = now;
                stateEnd = now + commandMs * 3;
                nextDrift = now + DRIFT_DELAY_MS;
                moving = false;
                splitCount++;
                log.info(String.format("[Jupiter] split %d (%s, phase %d): %d s, Command every %d s, gauge %d, "
                                + "mobs %s", splitCount, hard() ? "Hard" : "Normal", ctx.get("Phase"),
                        commandMs * 3 / 1000, commandMs / 1000, ctx.get("MagicSyncGauge"), ids(splitMobs)));
            }
        });
    }

    private void merge() {
        Field field = getField();
        if (field == null || isBossDead()) {
            return;
        }
        int level = ctx.get("MagicSyncLevel");
        int form = level < 0 ? AUSPICIOUS : level > 0 ? RUINOUS : BALANCED;
        if (isSpecialMerge() && form == BALANCED) {
            form = specialPicks++ % 2 == 0 ? AUSPICIOUS : RUINOUS; // inferred: the clear showed Auspicious, then Ruinous
            ctx.set("SpecialMergeTypeFlag", form == AUSPICIOUS ? 1 : 2);
        }
        command(); // the third Command lands with the merge
        moving = true;
        mergeCount++;
        merged = form;
        lastForm = form;
        ctx.set("Merged", form);
        ctx.set("MergeHistory2", ctx.get("MergeHistory1"));
        ctx.set("MergeHistory1", ctx.get("MergeHistory0"));
        ctx.set("MergeHistory0", form - 1);
        ctx.set("ShowBiasLvGauge", form == BALANCED ? 1 : 0);
        ctx.set("SpecialMergeFlag", 0);
        ctx.set("MagicSyncGauge", 500);
        updateLevel();
        balanceAtMerge(form);
        warned = false;
        queuePattern(controller, CAT_CONTROLLER, 0);
        despawnAll(splitMobs);
        jupiter = null;
        entity = null;
        Portal mid = field.getInfo().getPortalByName(MIDDLE_PORTAL);
        for (Char chr : new ArrayList<>(field.getChars())) {
            teleport(chr, mid);
            chr.write(FieldPacket.setTemporarySkillSet(0));
        }
        int f = form;
        int lv = level;
        later(MOVE_MS, () -> {
            synchronized (this) {
                if (isBossDead() || getField() != field) {
                    return;
                }
                Mob h = holderMob();
                if (h != null) {
                    fused = spawnLinked(base() + FORM[f], MIDDLE, h);
                    mergedMobs.add(fused);
                    for (int off : FORM_HELPERS[f]) {
                        spawnHelper(off, MIDDLE, false, mergedMobs);
                    }
                }
                if (f == BALANCED) {
                    weather("Without balance, you will be lost.", WEATHER_WARN_MS);
                }
                long now = System.currentTimeMillis();
                stateSince = now;
                stateEnd = now + MERGE_MS;
                moving = false;
                log.info(String.format("[Jupiter] merge %d (phase %d): level %d -> %s%s, %d s, mobs %s", mergeCount,
                        ctx.get("Phase"), lv, FORM_NAMES[f], ctx.get("SpecialMergeTypeFlag") > 0 ? " (special)" : "",
                        MERGE_MS / 1000, ids(mergedMobs)));
            }
        });
    }

    // ---- Command and the Magic Sync gauge ----

    private void command() {
        commandsDone++;
        ctx.set("SplitLuppiterCommandCount", Math.min(3, commandsDone));
        int level = ctx.get("MagicSyncLevel");
        int abs = Math.abs(level);
        boolean late = ctx.get("Phase") >= 3;
        boolean shown = queuePattern(commander, CAT_COMMAND, 0);
        log.info(String.format("[Jupiter] Command %d: gauge %d level %d -> Disruption %+d%s", commandsDone,
                ctx.get("MagicSyncGauge"), level, abs == 0 ? COMMAND_SUCCESS : COMMAND_FAIL[abs],
                shown ? "" : " (no controller for the Command art)"));
        if (abs == 0) {
            changeAllDisruption(COMMAND_SUCCESS);
            weather(late ? "Pay attention." : "Balance within.", WEATHER_COMMAND_MS);
            notice(late ? "The battlefield settles, decreasing Disruption, but the sync gauge is altered."
                    : "The battlefield settles, decreasing Disruption.");
        } else {
            changeAllDisruption(COMMAND_FAIL[abs]);
            weather(late ? "Face hardship." : "Pathetic. You'll come to regret that.", WEATHER_COMMAND_MS);
            notice(late ? "The battlefield grows unstable, increasing Disruption and altering the sync gauge."
                    : "The battlefield grows unstable, increasing Disruption.");
        }
        if (late) {
            changeGauge(Util.succeedProp(50) ? P3_GAUGE_SHIFT : -P3_GAUGE_SHIFT);
        }
    }

    private void changeGauge(int delta) {
        if (merged != SPLIT || moving) {
            return;
        }
        ctx.add("MagicSyncGauge", delta);
        updateLevel();
    }

    private void updateLevel() {
        int g = ctx.get("MagicSyncGauge");
        int level = g <= 200 ? -3 : g <= 300 ? -2 : g < 400 ? -1 : g <= 600 ? 0 : g <= 700 ? 1 : g <= 800 ? 2 : 3;
        ctx.set("MagicSyncLevel", level);
        if (Math.abs(level) >= 2) {
            ctx.set("MagicSyncLevelAchievement", 1);
        }
    }

    /**
     * Buff 80004093 at |level| 2 / 3 while split: 5 / 10 % (Hard 10 / 15 %). Its stat is not in the data: taken as
     * extra damage from the fallback attacks.
     */
    private int syncBuffPercent() {
        if (merged != SPLIT) {
            return 0;
        }
        int abs = Math.abs(ctx.get("MagicSyncLevel"));
        if (abs < 2) {
            return 0;
        }
        return (hard() ? 5 : 0) + (abs == 2 ? 5 : 10);
    }

    private void syncHit(Char chr, int zone) {
        long now = System.currentTimeMillis();
        Long last = lastSyncHit.get(chr.getId());
        if (last != null && now - last < HIT_THROTTLE_MS) {
            return;
        }
        lastSyncHit.put(chr.getId(), now);
        changeGauge(zone == 0 ? HIT_JUPITER_SIDE : HIT_ENTITY_SIDE);
    }

    @Override
    protected synchronized void onFallbackHit(Char chr, FallbackAttack attack) {
        if (merged == SPLIT) {
            syncHit(chr, ctx.get(chr, "ZoneType"));
        }
    }

    /** A pattern hit while split: the gauge change of the hitting mob's current run (graph 3 H2). */
    @Override
    public void onChrHitByMob(Char chr, int mobTemplateId, int dmgAmount) {
        super.onChrHitByMob(chr, mobTemplateId, dmgAmount);
        Field field = chr.getField();
        if (dmgAmount <= 0 || !inFight(field)) {
            return;
        }
        int off = mobTemplateId - base();
        if (off != JUPITER && off != JUPITER_HELPER && off != ENTITY && off != ENTITY_HELPER && off != SMALL_A
                && off != SMALL_B) {
            return;
        }
        Mob mob = null;
        for (Mob m : new ArrayList<>(field.getMobs())) {
            if (m.getTemplateId() == mobTemplateId && !m.isDead()) {
                mob = m;
                break;
            }
        }
        if (mob == null) {
            return;
        }
        int run = MobPatternDriver.lastRun(mob);
        long runAt = MobPatternDriver.lastRunAt(mob);
        long now = System.currentTimeMillis();
        if (run < 0 || now - runAt > RUN_HIT_WINDOW_MS) {
            return;
        }
        int key = run / 100000 * 1000 + run % 1000;
        synchronized (this) {
            if (merged != SPLIT || moving) {
                return;
            }
            long[] h = runHits.get(chr.getId());
            if (h == null || h[0] != mob.getObjectId() || h[1] != runAt) {
                h = new long[]{mob.getObjectId(), runAt, 0, 0};
                runHits.put(chr.getId(), h);
            } else if (now - h[3] < PATTERN_HIT_THROTTLE_MS) {
                return;
            }
            int sub = (int) Math.min(h[2], MAX_SUB.getOrDefault(key, 0));
            h[2]++;
            h[3] = now;
            int delta = GAUGE_BY_HIT.getOrDefault(key * 10 + sub, 0);
            if (delta != 0) {
                changeGauge(delta);
            }
            if (hitsLogged++ < HIT_LOG_MAX) {
                log.info(String.format("[Jupiter] %s hit by %d pattern %d sub %d: gauge %+d -> %d (level %d)",
                        chr.getName(), mobTemplateId, run, sub, delta, ctx.get("MagicSyncGauge"),
                        ctx.get("MagicSyncLevel")));
            }
            ctx.flush(field, false);
        }
    }

    // ---- Disruption and the Balance gauge ----

    private void changeAllDisruption(int delta) {
        Field field = getField();
        if (field == null) {
            return;
        }
        for (Char chr : new ArrayList<>(field.getChars())) {
            changeDisruption(chr, delta);
        }
    }

    private void changeDisruption(Char chr, int delta) {
        if (ctx.get(chr, "DeathCount") >= DISRUPTION_MAX) {
            return;
        }
        if (ctx.add(chr, "DeathCount", delta) >= DISRUPTION_MAX) {
            log.info("[Jupiter] " + chr.getName() + ": Disruption " + DISRUPTION_MAX + ", out of the fight");
            chr.chatMessage("Your Disruption has reached " + DISRUPTION_MAX + ". You can no longer fight.");
            if (chr.getHP() > 0) {
                EventManager.addEvent(chr::die, 500);
            }
        }
    }

    /** Auspicious raises the Balance gauge by 1, Ruinous fills it (inferred from the Disruption steps). */
    private void balanceAtMerge(int form) {
        int bg = ctx.get("BalanceGauge");
        if (form == AUSPICIOUS) {
            bg = ctx.set("BalanceGauge", bg + 1);
        } else if (form == RUINOUS) {
            bg = ctx.set("BalanceGauge", 2);
        }
        if (bg > 0) {
            int n = ctx.add("MergeCountWhenBalanceNotZero", 1);
            ctx.set("RollBackDeathCountPercent", n <= 2 ? 80 : n == 3 ? 50 : n == 4 ? 20 : 0);
            long now = System.currentTimeMillis();
            if (nextBalanceTick < now) {
                nextBalanceTick = now + BALANCE_TICK_MS;
            }
        }
    }

    /** After a Balanced merge the gauge empties and part of its Disruption comes back; a full gauge adds 100. */
    private void balanceAtSplit() {
        Field field = getField();
        if (field == null) {
            return;
        }
        if (lastForm == BALANCED && ctx.get("BalanceGauge") > 0) {
            int pct = ctx.get("RollBackDeathCountPercent");
            for (Char chr : new ArrayList<>(field.getChars())) {
                int back = ctx.get(chr, "RollBackDeathCount") * pct / 100;
                ctx.set(chr, "RollBackDeathCount", 0);
                if (back > 0 && ctx.get(chr, "DeathCount") < DISRUPTION_MAX) {
                    ctx.add(chr, "DeathCount", -back);
                }
            }
            ctx.set("BalanceGauge", 0);
            ctx.set("MergeCountWhenBalanceNotZero", 0);
            ctx.set("RollBackDeathCountPercent", 0);
        } else if (ctx.get("BalanceGauge") == 2) {
            changeAllDisruption(100);
        }
    }

    private void balanceTick() {
        Field field = getField();
        if (field == null) {
            return;
        }
        boolean full = ctx.get("BalanceGauge") == 2;
        for (Char chr : new ArrayList<>(field.getChars())) {
            if (ctx.get(chr, "DeathCount") >= DISRUPTION_MAX) {
                continue;
            }
            changeDisruption(chr, full ? 10 : 5);
            ctx.add(chr, "RollBackDeathCount", full ? 5 : 2);
        }
    }

    @Override
    public void onChrDeath(Char chr) {
        if (inFight(chr.getField()) && !isBossDead()) {
            synchronized (this) {
                changeDisruption(chr, merged == SPLIT ? DEATH_SPLIT : DEATH_MERGED);
                log.info("[Jupiter] " + chr.getName() + " died (" + FORM_NAMES[merged] + "): Disruption "
                        + ctx.get(chr, "DeathCount"));
                ctx.flush(getField(), false);
            }
            if (!canReviveInFight(chr)) {
                // the town revive window has no timer and may never be answered
                Field field = chr.getField();
                EventManager.addEvent(() -> {
                    if (chr.getField() == field && chr.getHP() <= 0) {
                        UserStatHandler.reviveInBoss(chr);
                    }
                }, OUT_AFTER_MS);
            }
        }
        super.onChrDeath(chr);
    }

    @Override
    public boolean canReviveInFight(Char chr) {
        return ctx.get(chr, "DeathCount") < DISRUPTION_MAX;
    }

    // ---- zones and Aether Warp ----

    private int zoneCount(int zone, Char except) {
        Field field = getField();
        int n = 0;
        if (field != null) {
            for (Char c : field.getChars()) {
                if (c != except && ctx.get(c, "ZoneType") == zone) {
                    n++;
                }
            }
        }
        return n;
    }

    private void moveToZone(Char chr) {
        Field field = getField();
        if (field != null) {
            teleport(chr, field.getInfo().getPortalByName(ZONE_PORTALS[ctx.get(chr, "ZoneType")]));
        }
    }

    private static void teleport(Char chr, Portal portal) {
        if (portal == null) {
            return;
        }
        Position to = new Position(portal.getX(), portal.getY());
        chr.setPosition(to);
        // the position form; the portal-id form was read but did not move the player here
        chr.write(FieldPacket.teleport(to, chr));
    }

    private void sendBar(Char chr) {
        chr.write(FieldPacket.setTemporarySkillSet(TEMP_BAR, AETHER_WARP));
    }

    @Override
    public void onSkillUse(Char chr, int skillId, int slv) {
        super.onSkillUse(chr, skillId, slv);
        if (skillId != AETHER_WARP || !inFight(chr.getField()) || chr.getHP() <= 0) {
            return;
        }
        synchronized (this) {
            if (merged != SPLIT || moving || isBossDead()) {
                return;
            }
            long now = System.currentTimeMillis();
            if (now < warpReadyAt.getOrDefault(chr.getId(), 0L)) {
                return;
            }
            int to = 1 - ctx.get(chr, "ZoneType");
            if (zoneCount(to, chr) >= 2) {
                chr.chatMessage("The other side of the barrier is too crowded for you to move.");
                return;
            }
            Field field = getField();
            int players = Math.max(1, Math.min(3, field == null ? 1 : field.getChars().size()));
            long cd = WARP_COOLDOWN_MS[players - 1];
            warpReadyAt.put(chr.getId(), now + cd);
            chr.addSkillCoolTime(AETHER_WARP, (int) cd);
            ctx.set(chr, "ZoneType", to);
            moveToZone(chr);
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            Option o = new Option();
            o.nOption = 1;
            o.rOption = AETHER_WARP;
            o.tOption = 1; // 1 s
            tsm.putCharacterStatValue(CharacterTemporaryStat.IndieNotDamaged, o, true);
            tsm.sendSetStatPacket();
            chr.chatMessage("Moves you to the other side of Jupiter's barrier.");
            log.info("[Jupiter] " + chr.getName() + " Aether Warp -> " + (to == 0 ? "up" : "down") + ", cooldown "
                    + cd / 1000 + " s");
            ctx.flush(field, false);
        }
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        super.onWarp(chr, oldField, newField);
        if (inFight(newField)) {
            synchronized (this) {
                ctx.join(chr);
                // no side choice before the fight: Jupiter's side first, two per side
                if (zoneCount(0, chr) >= 2) {
                    ctx.set(chr, "ZoneType", 1);
                }
            }
            EventManager.addEvent(() -> {
                if (chr.getField() != newField) {
                    return;
                }
                synchronized (this) {
                    if (merged == SPLIT) {
                        moveToZone(chr);
                        if (started) {
                            sendBar(chr);
                        }
                    } else {
                        teleport(chr, newField.getInfo().getPortalByName(MIDDLE_PORTAL));
                    }
                }
                ctx.send(chr);
            }, 1000); // after the client sets the field up
        } else if (inFight(oldField)) {
            chr.write(FieldPacket.setTemporarySkillSet(0));
        }
    }

    // ---- mobs ----

    private Mob holderMob() {
        Field field = getField();
        if (field == null) {
            return null;
        }
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            if (mob.getTemplateId() == base() && !mob.isDead()) {
                return mob;
            }
        }
        return null;
    }

    private Mob spawnLinked(int templateId, Position pos, Mob holder) {
        Mob mob = spawnAdd(templateId, pos, holder.getMaxHp());
        if (mob != null) {
            mob.setHpLinkMob(holder);
            mob.setHp(Math.max(1, holder.getHp()));
        }
        return mob;
    }

    /** An invisible helper (or small object) of the mode; its patterns are left to the server when manual. */
    private Mob spawnHelper(int offset, Position pos, boolean manual, List<Mob> group) {
        Mob mob = spawnAdd(base() + offset, pos, 0);
        if (mob == null) {
            log.warn("[Jupiter] could not spawn " + (base() + offset));
            return null;
        }
        if (manual) {
            MobPatternDriver.setManual(mob);
        }
        if (group != null) {
            group.add(mob);
        }
        return mob;
    }

    private void despawnAll(List<Mob> mobs) {
        for (Mob m : mobs) {
            despawn(m);
        }
        mobs.clear();
    }

    /** Queues a server-scheduled pattern (Command, merge / split) on its helper: the art only, the result is ours. */
    private boolean queuePattern(Mob mob, int category, int id) {
        return mob != null && MobPatternDriver.queueNow(mob, category * 100000 + mode() * 1000 + id);
    }

    private static String ids(List<Mob> mobs) {
        StringBuilder sb = new StringBuilder();
        for (Mob m : mobs) {
            if (m != null) {
                sb.append(sb.isEmpty() ? "" : " ").append(m.getTemplateId());
            }
        }
        return sb.toString();
    }

    @Override
    protected void onBossDeath(Mob boss) {
        super.onBossDeath(boss);
        synchronized (this) {
            log.info(String.format("[Jupiter] %s cleared after %d splits / %d merges", hard() ? "Hard" : "Normal",
                    splitCount, mergeCount));
            despawnAll(splitMobs);
            despawnAll(mergedMobs);
            despawn(controller);
            despawn(commander);
            Field field = getField();
            if (field != null) {
                for (Char chr : new ArrayList<>(field.getChars())) {
                    chr.write(FieldPacket.setTemporarySkillSet(0));
                }
            }
        }
    }

    // ---- fallback attacks ----

    /** Fallback attacks per state (no client data: estimates in % of max HP), raised by the Magic Sync buff. */
    @Override
    protected FallbackAttack[] fallbackAttacks(int stage, int fighterId) {
        boolean hard = hard();
        FallbackAttack[] a = switch (merged) {
            case SPLIT -> new FallbackAttack[]{new FallbackAttack(hard ? 30 : 25, 14_000, 2000, 350),
                    new FallbackAttack(35, 18_000, 1500, 300), new FallbackAttack(30, 18_000, 1300, 400),
                    new FallbackAttack(hard ? 50 : 40, 24_000, 2200, 0)};
            case AUSPICIOUS -> new FallbackAttack[]{new FallbackAttack(hard ? 40 : 35, 18_000, 1500, 400),
                    new FallbackAttack(35, 20_000, 1400, 350), new FallbackAttack(hard ? 70 : 60, 26_000, 2200, 0)};
            case BALANCED -> new FallbackAttack[]{new FallbackAttack(hard ? 35 : 30, 10_000, 1500, 300),
                    new FallbackAttack(40, 18_000, 1400, 350), new FallbackAttack(30, 20_000, 1200, 450),
                    new FallbackAttack(hard ? 70 : 60, 32_000, 2200, 0)};
            default -> new FallbackAttack[]{new FallbackAttack(hard ? 40 : 35, 10_000, 1300, 400),
                    new FallbackAttack(30, 8_000, 1000, 250), new FallbackAttack(40, 12_000, 1400, 350),
                    new FallbackAttack(hard ? 80 : 70, 22_000, 2200, 0)};
        };
        int buff = syncBuffPercent();
        if (buff == 0) {
            return a;
        }
        FallbackAttack[] up = new FallbackAttack[a.length];
        for (int i = 0; i < a.length; i++) {
            up[i] = new FallbackAttack(Math.min(99, a[i].damR() * (100 + buff) / 100), a[i].cooldownMs(),
                    a[i].warnMs(), a[i].radius());
        }
        return up;
    }

    private void weather(String text, int ms) {
        Field field = getField();
        if (field != null) {
            field.broadcastPacket(WvsContext.weatherEffectNotice(WeatherEffNoticeType.BossLuppiter, text, ms));
        }
    }

    @Override
    protected int rewardFieldId() {
        return BossConstants.JUPITER_FIELDS[mode()][1];
    }

    @Override
    public boolean usesField(int fieldId) {
        for (int[] mode : BossConstants.JUPITER_FIELDS) {
            for (int id : mode) {
                if (id == fieldId) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected int chestId() {
        return base() + BossConstants.JUPITER_CHEST;
    }

    @Override
    protected Position chestPos() {
        return new Position(0, 0);
    }

    @Override
    protected String bossName() {
        return "Jupiter";
    }
}
