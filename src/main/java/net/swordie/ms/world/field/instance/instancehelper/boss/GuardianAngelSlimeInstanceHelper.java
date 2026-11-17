package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.connection.packet.field.*;
import net.swordie.ms.connection.packet.field.slime.GuardianSlimeResult;
import net.swordie.ms.world.field.fieldskill.GuardianSlimeMagmaThrow;
import net.swordie.ms.world.field.fieldskill.GuardianSlimeRain;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.enums.WeatherEffNoticeType;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;
import net.swordie.ms.world.field.instance.Instance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import static net.swordie.ms.world.field.instance.instancehelper.boss.GuardianAngelSlimeData.*;

/**
 * Guardian Angel Slime, as GMS v271 runs it (client field 0x142d49510, Out 2235). Waiting room 160080000, intro
 * (160030000 Normal / 160010000 Chaos), the fight (160040000 / 160020000, fieldType 279), then the Silent Altar
 * (160060000 / 160050000) with the Slime Treasure Chest.
 * <ul>
 *     <li>Surface (90 s): field skill 100023 throws Magma Slimes and leaves 4 spots, one on each ledge pair. Players
 *     fill their gauge, pick a Magma Slime up and shoot it into the four crystals. Field skill 100025 rains slimes
 *     on the ledges.</li>
 *     <li>Guardian Wave: the floor opens onto one of four pit layouts and players block the wave with Crystal Drops.
 *     The client reports each gate (In 1703). A blocked wave stuns the slime for 15 s (damage meter on); either
 *     way there are 10 s to climb back before the floor closes.</li>
 * </ul>
 */
public class GuardianAngelSlimeInstanceHelper extends BossMechanicsHelper {

    public static final int WAITING_ROOM = 160080000;
    public static final int INTRO_NORMAL = 160030000;
    public static final int INTRO_CHAOS = 160010000;
    public static final int FIGHT_NORMAL = 160040000;
    public static final int FIGHT_CHAOS = 160020000;
    public static final String CHAOS_KEY = "slimeChaos";

    public static final int BOSS_X = 531, BOSS_Y = -1089;
    private static final int SURFACE_MS = 90_000;
    private static final int GROGGY_MS = 15_000;
    private static final int CLIMB_MS = 10_400;
    private static final int GAUGE_STEP = 25;
    private static final int GAUGE_TICK_MS = 2_200;
    private static final int MAGMA_SLIME_CHAOS_MOB = 8880702;
    private static final int TROUBLEMAKER_SLIME_CHAOS_MOB = 8880704;
    private static final int EXPLOSIVE_SLIME_CHAOS_MOB = 8880705;
    private static final int GOLD_SLIME_CHAOS_MOB = 8880706;
    private static final int ROMANTIC_SLIME_CHAOS_MOB = 8880707;
    private static final int HALO_SLIME_CHAOS_MOB = 8880708;
    private static final int MAGMA_SLIME_MOB = 8880713;
    private static final int TROUBLEMAKER_SLIME_MOB = 8880715;
    private static final int EXPLOSIVE_SLIME_MOB = 8880716;
    private static final int GOLD_SLIME_MOB = 8880717;
    private static final int ROMANTIC_SLIME_MOB = 8880718;
    private static final int HALO_SLIME_MOB = 8880719;

    /** Ledges 1-8: first spot x (three spots 140 px apart) and floor y. */
    private static final int[][] LEDGES = {{317, -1937}, {278, -1638}, {928, -1638}, {807, -1937},
            {284, -1089}, {337, -1382}, {858, -1089}, {829, -1382}};
    private static final int[] RAIN_Y = {-1942, -1643, -1387, -1094};
    private static final int[] PIT_RAIN_Y = {-922, -727, -559, -426, -278, -123, 56};

    private final List<ScheduledFuture> phaseTimers = Collections.synchronizedList(new ArrayList<>());
    private final Map<Integer, Integer> gauges = new ConcurrentHashMap<>();
    private final Map<Integer, ScheduledFuture> gaugeTimers = new ConcurrentHashMap<>();
    private final Set<Integer> spots = ConcurrentHashMap.newKeySet();
    private final int[] crystals = new int[4];
    private volatile boolean started, wave, groggy;
    private int spotSeq, launcherSeq = 59, dropSeq = 59, layout = -1;
    private long groggyDamage;

    private boolean isChaos() {
        return getInstance() != null && getInstance().hasProperty(CHAOS_KEY);
    }

    public int fightFieldId() {
        return isChaos() ? FIGHT_CHAOS : FIGHT_NORMAL;
    }

    public int introFieldId() {
        return isChaos() ? INTRO_CHAOS : INTRO_NORMAL;
    }

    public int altarFieldId() {
        return isChaos() ? BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_FIELD : BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_FIELD;
    }

    private int magmaId() {
        return isChaos() ? MAGMA_SLIME_CHAOS_MOB : MAGMA_SLIME_MOB;
    }

    private int[] summonIds() {
        return isChaos() ? new int[]{TROUBLEMAKER_SLIME_CHAOS_MOB, EXPLOSIVE_SLIME_CHAOS_MOB, ROMANTIC_SLIME_CHAOS_MOB, HALO_SLIME_CHAOS_MOB} : new int[]{TROUBLEMAKER_SLIME_MOB, EXPLOSIVE_SLIME_MOB, ROMANTIC_SLIME_MOB, HALO_SLIME_MOB};
    }

    private int goldId() {
        return isChaos() ? GOLD_SLIME_CHAOS_MOB : GOLD_SLIME_MOB;
    }

    @Override
    protected Field getField() {
        Instance instance = getInstance();
        return instance == null ? null : instance.getField(fightFieldId());
    }

    @Override
    public boolean usesField(int fieldId) {
        return fieldId == WAITING_ROOM || fieldId == INTRO_NORMAL || fieldId == INTRO_CHAOS || fieldId == FIGHT_NORMAL
                || fieldId == FIGHT_CHAOS || fieldId == BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_FIELD
                || fieldId == BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_FIELD;
    }

    @Override
    protected Set<Integer> getBossIds() {
        return Set.of(BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_MOB, BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_MOB);
    }

    /** Nothing at the waiting room's stage timer: the fight map's first-enter script calls startFight. */
    @Override
    protected void startMechanics() {
    }

    private void broadcast(OutPacket outPacket) {
        Field field = getField();
        if (field != null) {
            field.broadcastPacket(outPacket);
        }
    }

    private void run(Runnable task) {
        if (isBossDead() || getField() == null) {
            return;
        }
        try {
            task.run();
        } catch (Exception e) {
            log.error("Guardian Angel Slime phase failed", e);
        }
    }

    private void phaseLater(long delayMs, Runnable task) {
        phaseTimers.add(EventManager.addEvent(() -> run(task), delayMs));
    }

    private void phaseEvery(long firstMs, long intervalMs, Runnable task) {
        phaseTimers.add(EventManager.addFixedRateEvent(() -> run(task), firstMs, intervalMs));
    }

    private void cancelPhase() {
        synchronized (phaseTimers) {
            phaseTimers.forEach(sf -> sf.cancel(false));
            phaseTimers.clear();
        }
    }

    // region Fight flow

    /** Waiting room (ptBossIn) -> intro for everyone in the waiting room; the clock restarts at 30 min. */
    public synchronized void enterIntro() {
        Instance instance = getInstance();
        Field intro = instance.getField(introFieldId());
        for (Char c : new ArrayList<>(instance.getChars())) {
            if (c.getFieldID() == WAITING_ROOM) {
                c.warp(intro);
            }
        }
        instance.setTimeout(30 * 60);
    }

    /** Boss is up in the fight map (first_Slime*1pre): field setup, then the first surface phase 4 s later. */
    public synchronized void startFight(Field field) {
        if (started) {
            return;
        }
        started = true;
        field.spawnMob(magmaId(), 12, -1086, false, 0); // the field skills' reference monster
        field.broadcastPacket(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.phase(1, 0)));
        field.broadcastPacket(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(OBJ_ALL, OBJ_ALL_ON)));
        field.broadcastPacket(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.groggyDamage(0)));
        field.broadcastPacket(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.setup(1)));
        field.broadcastPacket(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.phase(1, 3000)));
        field.broadcastPacket(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.crystalLayout()));
        field.broadcastPacket(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.goldenSlime(goldId(), 12, -1086)));
        field.broadcastPacket(FieldPacket.footholdsOnExcept(FH_ALL, FH_ALL_OFF));
        for (Char chr : field.getChars()) {
            resetGauge(chr);
        }
        phaseLater(4_000, this::surface);
        every(21_000, () -> summon(summonIds()[0]));
        every(35_000, () -> summon(summonIds()[Util.succeedProp(50) ? 1 : 2]));
        every(300_000, () -> summon(summonIds()[3]));
        every(45_000, () -> {
            double hp = getBossHpRatio();
            if (hp >= 0 && hp < 0.67 && countAlive(goldId()) == 0) {
                spawnAdd(goldId(), randomGround(), 0);
            }
        });
    }

    private void summon(int id) {
        if (!wave && countAlive(summonIds()) < 8) {
            spawnAdd(id, randomGround(), 0);
        }
    }

    private void surface() {
        cancelPhase();
        wave = false;
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(OBJ_ALL, OBJ_ALL_ON)));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(new String[0], false)));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(OBJ_HOLES, true)));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(OBJ_BOTTOM, false)));
        broadcast(FieldPacket.footholdsOnExcept(FH_ALL, FH_ALL_OFF));
        broadcast(FieldPacket.footholdsOnOff(FH_BOTTOM, false));
        broadcast(FieldPacket.footholdsOnOff(FH_HOLES, false));
        broadcast(FieldPacket.footholdsOnOff(FH_TOP, true));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.crystalDropsCleared()));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.phase(2, SURFACE_MS)));
        synchronized (crystals) {
            Arrays.fill(crystals, 0);
        }
        spots.clear();
        for (Char chr : getField().getChars()) {
            if (!gauges.containsKey(chr.getId())) { // came in after the fight started
                resetGauge(chr);
            }
        }
        phaseEvery(16_000, 15_700, this::magmaThrow);
        phaseEvery(9_000, 13_500, () -> slimeRain(false));
        phaseLater(SURFACE_MS, this::guardianWave);
    }

    private void magmaThrow() {
        double hp = getBossHpRatio();
        int n = hp >= 0.8 ? 1 : 2 + Util.getRandom(1);
        int[][] t = new int[n][];
        for (int i = 0; i < n; i++) {
            int y = Util.succeedProp(50) ? -1125 : -1672;
            t[i] = Util.succeedProp(50)
                    ? new int[]{36, y, Util.getRandom(1000, 2540), -Util.getRandom(480, 3400)}
                    : new int[]{1375, y, Util.getRandom(-1125, 150), -Util.getRandom(120, 3450)};
        }
        broadcast(FieldPacket.fieldSkill(new GuardianSlimeMagmaThrow(2, magmaId(), t)));
        int[][] s = new int[4][];
        for (int i = 0; i < 4; i++) {
            int ledge = 2 * i + Util.getRandom(1); // one of each pair: 1/2, 3/4, 5/6, 7/8
            int[] l = LEDGES[ledge];
            int id;
            synchronized (this) {
                id = ++spotSeq;
            }
            spots.add(id);
            s[i] = new int[]{id, ledge + 1, l[0] + 140 * Util.getRandom(2), l[1], Util.succeedProp(20) ? 1 : 0};
        }
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.magmaSpots(s, magmaId())));
    }

    private void slimeRain(boolean pit) {
        double hp = getBossHpRatio();
        int slv = hp >= 0 && hp <= 0.2 ? 1 : 2;
        int[][] p = new int[pit ? 20 : 10][];
        for (int i = 0; i < p.length; i++) {
            int y = i < 10 ? RAIN_Y[Util.getRandom(RAIN_Y.length - 1)] : PIT_RAIN_Y[Util.getRandom(PIT_RAIN_Y.length - 1)];
            p[i] = new int[]{Util.getRandom(-60, 1530), y};
        }
        broadcast(FieldPacket.fieldSkill(new GuardianSlimeRain(slv, magmaId(), p)));
    }

    private void guardianWave() {
        cancelPhase();
        wave = true;
        int prev = layout;
        int next;
        do {
            next = Util.getRandom(3);
        } while (next == prev);
        layout = next;
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.magmaClear()));
        spots.clear();
        if (prev >= 0) {
            broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(OBJ_SET[prev], false)));
        }
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(OBJ_BOTTOM, true)));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(OBJ_SET[next], true)));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(new String[0], false)));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(OBJ_HOLES, false)));
        if (prev >= 0) {
            broadcast(FieldPacket.footholdsOnOff(FH_SET[prev], true));
        }
        broadcast(FieldPacket.footholdsOnOff(FH_TOP, false));
        broadcast(FieldPacket.footholdsOnOff(FH_SET[next], false));
        broadcast(FieldPacket.footholdsOnOff(FH_BOTTOM, true));
        broadcast(FieldPacket.footholdsOnOff(FH_HOLES, true));
        broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.GuardianAngelSlime, "The Guardian Wave is going to fall! Set up the Crystal Drop to "
                + "control the flow of the wave. I'll help!", 5000));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.waveLayout(next)));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.pit(717, -1287, 717, -118, 500, 300, 4000)));
        List<Integer> order = new ArrayList<>(List.of(0, 1, 2, 3));
        Collections.shuffle(order);
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.crystalOrder(order.stream().mapToInt(Integer::intValue).toArray())));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.crystalDropConfig(20, 1000)));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.magmaPhase()));
        phaseEvery(8_000, 15_700, () -> slimeRain(true));
        phaseLater(11_200, () -> {
            broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.waveCreate(getField().getNewObjectID(), false)));
            phaseLater(30_000, () -> endWave(false)); // the client never reported the wave's end
        });
    }

    private synchronized void endWave(boolean blocked) {
        if (!wave) {
            return;
        }
        cancelPhase();
        wave = false;
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.objects(OBJ_HOLES, true)));
        broadcast(FieldPacket.footholdsOnOff(FH_HOLES, false));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.waveRemove()));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.crystalDropsCleared()));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.waveFlag(false)));
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.phase(4, 10_000)));
        Mob boss = getBoss();
        if (blocked) {
            broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.waveGateResult(1)));
            broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.GuardianAngelSlime, "You did it! Now get to higher ground. You've got 10 seconds!", 5000));
            if (boss != null) {
                broadcast(FieldPacket.changeMobZone(boss.getObjectId(), 0));
            }
        } else {
            broadcast(WvsContext.weatherEffectNotice(WeatherEffNoticeType.GuardianAngelSlime, "The bottom area will close in 10 seconds! Get to higher ground!", 5000));
        }
        phaseLater(CLIMB_MS, () -> {
            surface();
            if (blocked) {
                startGroggy();
            }
        });
    }

    private void startGroggy() {
        groggy = true;
        broadcast(FieldPacket.localRepeatEffect("Etc/BossGuardianSlime.img/groggyEffect", 1));
        later(GROGGY_MS, () -> {
            groggy = false;
            Mob boss = getBoss();
            if (boss != null) {
                broadcast(FieldPacket.changeMobZone(boss.getObjectId(), 1));
            }
        });
    }

    @Override
    public void onMobDamaged(Mob mob, Char dealer, long damage) {
        if (groggy && getBossIds().contains(mob.getTemplateId())) {
            long total;
            synchronized (this) {
                total = groggyDamage += damage;
            }
            broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.groggyDamage(total)));
        }
    }

    @Override
    protected void onBossDeath(Mob boss) {
        cancelPhase();
        gaugeTimers.values().forEach(sf -> sf.cancel(false));
        gaugeTimers.clear();
        Instance instance = getInstance();
        Field altar = instance.getField(altarFieldId());
        int chest = isChaos() ? BossConstants.GUARDIAN_ANGEL_SLIME_CHAOS_CHEST : BossConstants.GUARDIAN_ANGEL_SLIME_NORMAL_CHEST;
        altar.spawnMob(chest, 300, 316, false, BossConstants.GUARDIAN_ANGEL_SLIME_CHEST_HP);
        int fightId = fightFieldId();
        EventManager.addEvent(() -> {
            for (Char chr : new ArrayList<>(instance.getField(fightId).getChars())) {
                chr.warp(altar);
            }
            instance.setTimeout(295);
        }, 3_000);
    }

    // endregion

    // region Client requests

    private void resetGauge(Char chr) {
        int id = chr.getId();
        gauges.put(id, 0);
        chr.write(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.gauge(0)));
        ScheduledFuture old = gaugeTimers.remove(id);
        if (old != null) {
            old.cancel(false);
        }
        gaugeTimers.put(id, EventManager.addFixedRateEvent(() -> {
            int v = Math.min(GuardianSlimeResult.MAX_GAUGE, gauges.getOrDefault(id, 0) + GAUGE_STEP);
            gauges.put(id, v);
            chr.write(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.gauge(v)));
            if (v >= GuardianSlimeResult.MAX_GAUGE) {
                ScheduledFuture sf = gaugeTimers.remove(id);
                if (sf != null) {
                    sf.cancel(false);
                }
            }
        }, GAUGE_TICK_MS, GAUGE_TICK_MS));
    }

    /** In 1704: int type, body. */
    public void onRequest(Char chr, int type, InPacket in) {
        if (chr.getFieldID() != fightFieldId() || !started || isBossDead()) {
            return;
        }
        switch (type) {
            case 14 -> { // int chr: the full gauge was spent on a Magma Slime
                if (gauges.getOrDefault(chr.getId(), 0) >= GuardianSlimeResult.MAX_GAUGE) {
                    resetGauge(chr);
                }
            }
            case 5 -> { // int chr, byte, byte, int x, int y: Magma Slime picked up
                in.decodeInt();
                boolean b1 = in.decodeByte() != 0;
                boolean b2 = in.decodeByte() != 0;
                int x = in.decodeInt(), y = in.decodeInt();
                int id;
                synchronized (this) {
                    id = ++launcherSeq;
                }
                broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.launcher(id, chr.getId(), b1 ? 0 : b2 ? 3 : 2, x, y)));
            }
            case 7 -> broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.aim(in.decodeInt(), in.decodeInt(), in.decodeInt(),
                    in.decodeInt(), in.decodeInt())));
            case 9 -> {
                int id = in.decodeInt(), target = in.decodeInt();
                broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.launch(id, target, in.decodeDouble())));
            }
            case 4 -> { // int spot: used up
                int spot = in.decodeInt();
                if (spots.remove(spot)) {
                    broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.magmaSpotGone(spot)));
                }
            }
            case 12 -> { // int spot, int 2, int crystal: the Magma Slime hit a crystal
                int spot = in.decodeInt();
                in.decodeInt();
                int crystal = in.decodeInt();
                if (crystal >= 0 && crystal < crystals.length) {
                    int count;
                    synchronized (crystals) {
                        count = ++crystals[crystal];
                    }
                    broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.magmaCrystal(spot, crystal, count)));
                }
            }
            default -> {
            }
        }
    }

    /** In 1700: int value, int x, int y (a Crystal Drop placed during the wave). */
    public void onCrystalDrop(Char chr, int value, int x, int y) {
        if (!wave || chr.getFieldID() != fightFieldId()) {
            return;
        }
        int seq;
        synchronized (this) {
            seq = ++dropSeq;
        }
        broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.crystalDropPlaced(chr.getId(), seq, value, getField().getNewObjectID(), x, y)));
    }

    /** In 1703: int count, int value (the wave reached a gate; 1 = blocked). */
    public void onWaveResult(Char chr, int count, int value) {
        if (!wave || chr.getFieldID() != fightFieldId()) {
            return;
        }
        if (value == 1 || count >= 3) {
            endWave(value == 1);
        } else {
            broadcast(GuardianAngelSlimeFieldPacket.result(GuardianSlimeResult.waveGateResult(value)));
        }
    }

    // endregion
}
