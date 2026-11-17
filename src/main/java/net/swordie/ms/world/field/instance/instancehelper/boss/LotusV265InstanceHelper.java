package net.swordie.ms.world.field.instance.instancehelper.boss;

import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.connection.packet.field.FieldPacket;
import net.swordie.ms.connection.packet.field.LotusFieldPacket;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.constants.BossConstants;
import net.swordie.ms.handlers.PatternHotfixHandler;
import net.swordie.ms.handlers.header.OutHeader;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.util.Position;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The v265 Lotus, Normal / Hard / Extreme: three phase maps in a row, then the treasure room with the reward core.
 * The monsters have no Mob.wz skills; their attacks are the client's pattern system (Mob.wz BossPattern), so the
 * server spawns each phase, its invincible pattern monsters, and runs the phase's alert gauge.
 * <p>
 * Pattern monsters per phase (same offsets for every mode): phase 1 base + 4 (category 1001), + 10 (1006) and
 * + 11 (1007); phase 2 + 5 (1003) and + 12 (1008); phase 3 + 6 (1005) and + 13 (1009). The client ties these to
 * the phase by field type (0x141f38cc0 sends alert-mode events to 1001/1003/1005 and 1006/1008/1009 patterns).
 * <p>
 * Alert gauge (Out 2247, the phase body's Common/difficulty): rises by increase every tick ms; full = alert mode
 * (weather message 0, then 1 on later alerts) until it has fallen to min by alertDecrease every alertTick ms
 * (message 2). The purge routines (1006/1008/1009 pattern 000) are queued only in alert mode.
 * <p>
 * Phase 3 (1004) has decrease instead of increase and no alertDecrease. Its UI (0x142b3e460) draws only the second
 * gauge (type 2), as max - value, and ignores type 1; so the server keeps the same fill and sends max - fill as
 * type 2. Without alertDecrease it drains at phase 1's rate.
 */
public class LotusV265InstanceHelper extends StagedBossHelper {

    private static final boolean HELPERS =
            net.swordie.ms.ServerSettings.getBoolean("test.lotusHelpers", true);
    private static final boolean GAUGE =
            net.swordie.ms.ServerSettings.getBoolean("test.lotusAlertGauge", true);
    private static final int[][] HELPER_OFFSETS = {{4, 10, 11}, {5, 12}, {6, 13}};
    // the phase whose UI shows the second gauge
    private static final int SECOND_GAUGE_PHASE = 2;
    private static final int MODE_OFF = 0, MODE_ALERT = 1;
    // phase 3 (GMS): starts in maximum alert (2) with the second gauge at 0; full = safe mode (3, "System
    // overheated"), which drains back to 0 and maximum alert again. The purge routines run in maximum alert only.
    private static final int MODE_MAX_ALERT = 2, MODE_SAFE = 3;
    // fields whose gauge is in alert mode
    private static final Set<Field> alertFields = ConcurrentHashMap.newKeySet();

    private volatile int mode = -1;
    private Stage[] stages;
    private int helpersFor = -1;
    private volatile int gaugeFor = -1;
    private volatile Field gaugeField;
    private volatile int gauge;
    private volatile int gaugeMax = 1000;
    private volatile int alertMode;
    private int alerts;
    private long nextGaugeAt;

    private int mode() {
        if (mode < 0) {
            int m = getInstance() == null ? -1 : BossConstants.getLotusV265Mode(getInstance().getEnterFieldId());
            mode = Math.max(0, m);
        }
        return mode;
    }

    @Override
    protected synchronized Stage[] stages() {
        if (stages == null) {
            int m = mode();
            int f = BossConstants.LOTUS_V265_FIELD_BASE[m];
            int b = BossConstants.LOTUS_V265_MOB_BASE[m];
            long[] hp = BossConstants.LOTUS_V265_HP[m];
            stages = new Stage[]{
                    new Stage(f + 100, b, hp[0], new int[0], 0, 0, -16),
                    new Stage(f + 300, b + 1, hp[1], new int[0], 0, 450, 398),
                    new Stage(f + 500, b + 2, hp[2], new int[0], 0, 0, 396)};
        }
        return stages;
    }

    @Override
    protected int rewardFieldId() {
        return BossConstants.LOTUS_V265_FIELD_BASE[mode()] + 600;
    }

    @Override
    protected int chestId() {
        return BossConstants.lotusV265Core(mode());
    }

    @Override
    protected Position chestPos() {
        return new Position(0, 498);
    }

    @Override
    protected String bossName() {
        return "Lotus";
    }

    /** Whether this field's alert gauge is full (the purge routines may run). */
    public static boolean isAlert(Field field) {
        return field != null && alertFields.contains(field);
    }

    /** Patterns queued only in alert mode: pattern 000 of 1006/1008/1009 (purge routines). */
    public static boolean alertOnly(int patternId) {
        int category = patternId / 100000;
        return (category == 1006 || category == 1008 || category == 1009) && patternId % 1000 == 0;
    }

    @Override
    protected void startMechanics() {
        super.startMechanics();
        every(250, this::phaseTick);
    }

    private Mob alive(Field field, int templateId) {
        for (Mob mob : new ArrayList<>(field.getMobs())) {
            if (mob.getTemplateId() == templateId && !mob.isDead()) {
                return mob;
            }
        }
        return null;
    }

    private synchronized void phaseTick() {
        int phase = stageIndex();
        Stage[] st = stages();
        Field field = getField();
        // the scene before a phase runs ~20 s: the gauge and pattern monsters wait for a player in the map
        if (phase >= st.length || field == null || field.getId() != st[phase].fieldId()
                || field.getChars().isEmpty()) {
            return;
        }
        Mob body = alive(field, st[phase].holderId());
        if (body == null) {
            return;
        }
        if (HELPERS && helpersFor != phase) {
            helpersFor = phase;
            int base = BossConstants.LOTUS_V265_MOB_BASE[mode()];
            StringBuilder sb = new StringBuilder();
            for (int off : HELPER_OFFSETS[phase]) {
                if (alive(field, base + off) == null && spawnAdd(base + off, body.getPosition().deepCopy(), 0) != null) {
                    sb.append(' ').append(base + off);
                }
            }
            log.info("[Lotus] phase " + (phase + 1) + " pattern monsters:" + sb);
        }
        if (GAUGE) {
            gaugeTick(field, phase);
        }
    }

    private void gaugeTick(Field field, int phase) {
        PatternHotfixHandler.AlertGauge g = PatternHotfixHandler.alertGauge(1000 + 2 * phase, mode());
        if (g == null) {
            return;
        }
        long now = System.currentTimeMillis();
        if (gaugeFor != phase) {
            if (gaugeField != null) {
                alertFields.remove(gaugeField);
            }
            gauge = g.min();
            gaugeMax = g.max();
            alertMode = phase == SECOND_GAUGE_PHASE ? MODE_MAX_ALERT : MODE_OFF;
            if (alertMode == MODE_MAX_ALERT) {
                alertFields.add(field);
            }
            nextGaugeAt = now + g.tick();
            gaugeField = field;
            gaugeFor = phase;
            field.broadcastPacket(create(phase));
            log.info(String.format("[Lotus] phase %d alert gauge: %d..%d, +%d / %d ms, alert -%d / %d ms",
                    phase + 1, g.min(), g.max(), rise(g), g.tick(), drain(g), g.alertTick()));
            return;
        }
        if (now < nextGaugeAt) {
            return;
        }
        if (phase == SECOND_GAUGE_PHASE) {
            thirdPhaseTick(field, g, now);
            return;
        }
        if (alertMode == MODE_OFF) {
            nextGaugeAt = now + Math.max(250, g.tick());
            gauge = Math.min(g.max(), gauge + rise(g));
            field.broadcastPacket(gaugePacket(phase, gauge));
            if (gauge >= g.max()) {
                alertMode = MODE_ALERT;
                alertFields.add(field);
                field.broadcastPacket(modePacket(MODE_ALERT));
                message(field, g, alerts++ == 0 ? 0 : 1);
                log.info("[Lotus] phase " + (phase + 1) + " alert gauge full: alert mode");
            }
        } else {
            nextGaugeAt = now + Math.max(250, g.alertTick());
            gauge = Math.max(g.min(), gauge - drain(g));
            field.broadcastPacket(gaugePacket(phase, gauge));
            if (gauge <= g.min()) {
                alertMode = MODE_OFF;
                alertFields.remove(field);
                field.broadcastPacket(modePacket(MODE_OFF));
                message(field, g, 2);
                log.info("[Lotus] phase " + (phase + 1) + " alert gauge drained: alert mode off");
            }
        }
    }

    private void thirdPhaseTick(Field field, PatternHotfixHandler.AlertGauge g, long now) {
        if (alertMode == MODE_MAX_ALERT) {
            // GMS: in maximum alert the gauge only rises from the pattern hits the clients report (In 1777)
            nextGaugeAt = now + Math.max(250, g.tick());
        } else {
            // safe mode drains by the phase's decrease every tick (1004: 25 / 30 / 40)
            nextGaugeAt = now + Math.max(250, g.tick());
            gauge = Math.max(g.min(), gauge - rise(g));
            field.broadcastPacket(gaugePacket(SECOND_GAUGE_PHASE, gauge));
            if (gauge <= g.min()) {
                alertMode = MODE_MAX_ALERT;
                alertFields.add(field);
                field.broadcastPacket(modePacket(MODE_MAX_ALERT));
                thirdPhaseMessage(field, g, 4, "System restore complete. Entering maximum alert mode.");
            }
        }
    }

    /**
     * In 1777 type 4 (GMS): a client reports a phase pattern's hit (key = category * 100000 + difficulty * 1000
     * + pattern, and a byte). Phases 1-2 (1001/1003): pattern 0 takes 70 (byte 1) or 200 (byte 0), pattern 1 takes 30;
     * phase 3 (1005): pattern 0 adds 60 per byte (200 for byte 0), pattern 1 adds 40, pattern 2 adds 60.
     */
    public synchronized void onPatternHit(int key, int flag) {
        int phase = gaugeFor;
        Field field = gaugeField;
        if (field == null || phase < 0 || key / 100000 != 1001 + 2 * phase) {
            return;
        }
        PatternHotfixHandler.AlertGauge g = PatternHotfixHandler.alertGauge(1000 + 2 * phase, mode());
        if (g == null) {
            return;
        }
        int pattern = key % 1000;
        int delta;
        if (phase == SECOND_GAUGE_PHASE) {
            if (alertMode != MODE_MAX_ALERT) {
                return;
            }
            delta = pattern == 0 ? (flag == 0 ? 200 : 60 * flag) : pattern == 1 ? 40 : pattern == 2 ? 60 : 0;
        } else {
            if (alertMode != MODE_OFF) {
                return;
            }
            delta = pattern == 0 ? (flag == 1 ? -70 : -200) : pattern == 1 ? -30 : 0;
        }
        if (delta == 0) {
            return;
        }
        gauge = Math.max(g.min(), Math.min(g.max(), gauge + delta));
        field.broadcastPacket(gaugePacket(phase, gauge, key));
        if (phase == SECOND_GAUGE_PHASE && gauge >= g.max()) {
            alertMode = MODE_SAFE;
            alertFields.remove(field);
            field.broadcastPacket(modePacket(MODE_SAFE));
            thirdPhaseMessage(field, g, 3, "System overheated. Activating safe mode.");
        }
    }

    private void thirdPhaseMessage(Field field, PatternHotfixHandler.AlertGauge g, int index, String fallback) {
        if (index < g.messages().size()) {
            message(field, g, index);
        } else {
            notice(fallback);
        }
    }

    /** Fill per tick; phase 3's data gives it as decrease (a negative increase). */
    private static int rise(PatternHotfixHandler.AlertGauge g) {
        return Math.abs(g.increase());
    }

    /** Drain per alert tick; phase 3 has none, so it takes phase 1's of the same difficulty. */
    private int drain(PatternHotfixHandler.AlertGauge g) {
        if (g.alertDecrease() > 0) {
            return g.alertDecrease();
        }
        PatternHotfixHandler.AlertGauge first = PatternHotfixHandler.alertGauge(1000, mode());
        return first != null && first.alertDecrease() > 0 ? first.alertDecrease() : 40;
    }

    private void message(Field field, PatternHotfixHandler.AlertGauge g, int index) {
        if (index >= g.messages().size()) {
            return;
        }
        PatternHotfixHandler.GaugeMessage m = g.messages().get(index);
        if (m.system() != null && !m.system().isEmpty()) {
            notice(m.system());
        }
        if (m.weather() != null && !m.weather().isEmpty()) {
            field.broadcastPacket(WvsContext.weatherEffectNotice(m.type(), m.weather(), m.durationMs()));
        }
    }

    /** Out 2247 type 0: difficulty, phase (0..2), mode, gauge, second gauge (phase 3's, drawn as max - value). */
    private OutPacket create(int phase) {
        boolean second = phase == SECOND_GAUGE_PHASE;
        // GMS phase 3: mode 2, first gauge at max, second gauge 0
        return LotusFieldPacket.create(mode(), phase, alertMode, second ? gaugeMax : gauge, second ? gauge : 0);
    }

    /**
     * Out 2247 type 1 (type 2 in phase 3, as max - fill): gauge value; reason 0 is the client's "Update" label, not a
     * damage source.
     */
    private OutPacket gaugePacket(int phase, int value) {
        return gaugePacket(phase, value, 0);
    }

    /** reason: the pattern key that changed it (GMS), or 0 for the timed rise. */
    private OutPacket gaugePacket(int phase, int value, int reason) {
        // GMS sends the fill itself in both gauges
        return LotusFieldPacket.setGauge(phase == SECOND_GAUGE_PHASE, value, reason);
    }

    private static OutPacket modePacket(int mode) {
        return LotusFieldPacket.setMode(mode);
    }

    private int directionField(int index) {
        return BossConstants.LOTUS_V265_FIELD_BASE[mode()] + index * 200;
    }

    /**
     * Phases 2 and 3 start with their direction map (base +200 / +400), as GMS. Its field script
     * (suu_Nphase_direction) plays the scene and moves each player on when the client has played it.
     */
    @Override
    protected void moveToStage(int index) {
        if (getInstance() == null) {
            return;
        }
        if (index < 1 || index > 2) {
            super.moveToStage(index);
            return;
        }
        getInstance().warp(directionField(index));
    }

    @Override
    public void onWarp(Char chr, Field oldField, Field newField) {
        super.onWarp(chr, oldField, newField);
        int phase = gaugeFor;
        if (GAUGE && phase >= 0 && newField != null && newField == gaugeField) {
            EventManager.addEvent(() -> {
                if (chr.getField() == newField && gaugeFor == phase) {
                    chr.write(create(phase));
                }
            }, 1000); // after the client sets the field up
        }
    }

    @Override
    protected void onBossDeath(Mob boss) {
        super.onBossDeath(boss);
        Field f = gaugeField;
        if (f != null) {
            alertFields.remove(f);
        }
    }

    @Override
    public void clearInstance(boolean clearInstance) {
        Field f = gaugeField;
        if (f != null) {
            alertFields.remove(f);
        }
        super.clearInstance(clearInstance);
    }
}
