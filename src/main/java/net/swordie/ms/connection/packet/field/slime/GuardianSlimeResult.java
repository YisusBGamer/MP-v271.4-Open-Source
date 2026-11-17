package net.swordie.ms.connection.packet.field.slime;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.life.mob.skill.FieldSkillID;

/**
 * Guardian Angel Slime's field. Most types are an int sub-type and ints; the ones with more say so below.
 */
public class GuardianSlimeResult {

    public static final int MAX_GAUGE = 100;

    // Wave sub-types
    private static final int WAVE_CREATE = 0, WAVE_REMOVE = 1, WAVE_GATE_RESULT = 2, WAVE_FLAG = 5;
    // CrystalDrop sub-types; event 6 = a drop placed, 8 = drops cleared
    private static final int DROP_CONFIG = 0, DROP_EVENT = 1, DROP_PLACED = 6, DROP_CLEARED = 8;
    // Magma sub-types
    private static final int MAGMA_SPOTS = 1, MAGMA_SPOT_GONE = 2, MAGMA_CLEAR = 3, MAGMA_LAUNCHER = 6,
            MAGMA_AIM = 8, MAGMA_LAUNCH = 10, MAGMA_CRYSTAL = 13;
    // Crystals sub-types
    private static final int CRYSTAL_LAYOUT = 0, CRYSTAL_ORDER = 1;
    private static final int PHASE_MAGMA = 3;
    private static final int WAVE_LAYOUT_LAYER = 10;
    private static final int AIM_RANGE = 20;

    /** The Guardian Wave at its start spot, as GMS sends it. */
    private static final int[] WAVE_SPOT = {692, -2199, 10, 15, 0, 0, 0, -500, 20, 1000, 3, 1000, 1000};
    /** Four crystals on the eight ledges, as GMS sends it. */
    private static final int[] CRYSTAL_LAYOUT_VALUES = {3, 8, 36, -1639, 1375, -1639, 36, -1092, 1375, -1092, 36,
            -288, 1393, -288, 36, 55, 1393, 55, 2, 2, 2, 2, 1, 1, 1, 1, 0, 1, 2, 3, 0, 0, 0, 0, 16777472, 16777472}; // hardcode-ok
    /** Launch: speed and timing values GMS sends after the angle. */
    private static final int[] LAUNCH_VALUES = {15, 320, 11, 10};
    private static final double LAUNCH_SCALE = 1.0;
    private static final int MAGMA_SPOT_DELAY = 1080;
    private static final int MAGMA_SPOT_ACTION = 3;

    private final GuardianSlimeType type;
    private int[] ints = new int[0];
    private boolean flag;
    private long value;
    private double angle;
    private String[] tags;
    private boolean[] on;
    private int[][] spots;
    private int magmaId;

    private GuardianSlimeResult(GuardianSlimeType type, int... ints) {
        this.type = type;
        this.ints = ints;
    }

    // --- Guardian Wave

    public static GuardianSlimeResult waveCreate(int objectId, boolean flag) {
        int[] v = new int[WAVE_SPOT.length + 2];
        v[0] = WAVE_CREATE;
        v[1] = objectId;
        System.arraycopy(WAVE_SPOT, 0, v, 2, WAVE_SPOT.length);
        GuardianSlimeResult r = new GuardianSlimeResult(GuardianSlimeType.Wave, v);
        r.flag = flag;
        return r;
    }

    public static GuardianSlimeResult waveRemove() {
        return new GuardianSlimeResult(GuardianSlimeType.Wave, WAVE_REMOVE);
    }

    public static GuardianSlimeResult waveGateResult(int result) {
        return new GuardianSlimeResult(GuardianSlimeType.Wave, WAVE_GATE_RESULT, result);
    }

    public static GuardianSlimeResult waveFlag(boolean flag) {
        GuardianSlimeResult r = new GuardianSlimeResult(GuardianSlimeType.Wave, WAVE_FLAG);
        r.flag = flag;
        return r;
    }

    public static GuardianSlimeResult waveLayout(int layout) {
        return new GuardianSlimeResult(GuardianSlimeType.WaveLayout, WAVE_LAYOUT_LAYER, layout);
    }

    // --- field setup

    /** The pit rect: 7 ints, as GMS sends them. */
    public static GuardianSlimeResult pit(int... rect) {
        return new GuardianSlimeResult(GuardianSlimeType.Pit, rect);
    }

    public static GuardianSlimeResult objects(String[] tags, boolean[] on) {
        GuardianSlimeResult r = new GuardianSlimeResult(GuardianSlimeType.Objects);
        r.tags = tags;
        r.on = on;
        return r;
    }

    public static GuardianSlimeResult objects(String[] tags, boolean on) {
        boolean[] all = new boolean[tags.length];
        java.util.Arrays.fill(all, on);
        return objects(tags, all);
    }

    public static GuardianSlimeResult setup(int value) {
        return new GuardianSlimeResult(GuardianSlimeType.Setup, value);
    }

    public static GuardianSlimeResult goldenSlime(int templateId, int value, int y) {
        return new GuardianSlimeResult(GuardianSlimeType.GoldenSlime, templateId, value, y);
    }

    public static GuardianSlimeResult phase(int phase, int ms) {
        return new GuardianSlimeResult(GuardianSlimeType.Phase, phase, ms);
    }

    /** Phase 3: the Magma Slime throws (field skill 100023). */
    public static GuardianSlimeResult magmaPhase() {
        return new GuardianSlimeResult(GuardianSlimeType.Phase, PHASE_MAGMA, 0,
                FieldSkillID.GuardianSlimeMagmaThrow.getId());
    }

    // --- Crystal Drop

    public static GuardianSlimeResult crystalDropConfig(int count, int ms) {
        return new GuardianSlimeResult(GuardianSlimeType.CrystalDrop, DROP_CONFIG, count, ms);
    }

    public static GuardianSlimeResult crystalDropPlaced(int chrId, int seq, int value, int objectId, int x, int y) {
        return new GuardianSlimeResult(GuardianSlimeType.CrystalDrop, DROP_EVENT, DROP_PLACED, chrId, seq, value, 2,
                chrId, objectId, x, y);
    }

    public static GuardianSlimeResult crystalDropsCleared() {
        return new GuardianSlimeResult(GuardianSlimeType.CrystalDrop, DROP_EVENT, DROP_CLEARED, 0);
    }

    // --- Magma Slimes

    /** Per spot: id, ledge (1-8), x, y on the ledge's floor, big (0/1). */
    public static GuardianSlimeResult magmaSpots(int[][] spots, int magmaId) {
        GuardianSlimeResult r = new GuardianSlimeResult(GuardianSlimeType.Magma, MAGMA_SPOTS, spots.length);
        r.spots = spots;
        r.magmaId = magmaId;
        return r;
    }

    public static GuardianSlimeResult magmaSpotGone(int spot) {
        return new GuardianSlimeResult(GuardianSlimeType.Magma, MAGMA_SPOT_GONE, spot);
    }

    public static GuardianSlimeResult magmaClear() {
        return new GuardianSlimeResult(GuardianSlimeType.Magma, MAGMA_CLEAR);
    }

    /** A player picked up a Magma Slime; dir 0-3 sets the aim limits. */
    public static GuardianSlimeResult launcher(int id, int chrId, int dir, int x, int y) {
        int from = dir <= 1 ? 90 : -90;
        int to = dir == 1 || dir == 3 ? 90 : -90;
        return new GuardianSlimeResult(GuardianSlimeType.Magma, MAGMA_LAUNCHER, id, chrId, dir, x, y, from, to);
    }

    public static GuardianSlimeResult aim(int id, int x1, int y1, int x2, int y2) {
        return new GuardianSlimeResult(GuardianSlimeType.Magma, MAGMA_AIM, id, AIM_RANGE, x1, y1, x2, y2);
    }

    public static GuardianSlimeResult launch(int id, int target, double angle) {
        GuardianSlimeResult r = new GuardianSlimeResult(GuardianSlimeType.Magma, MAGMA_LAUNCH, id, target);
        r.angle = angle;
        return r;
    }

    public static GuardianSlimeResult magmaCrystal(int spot, int crystal, int count) {
        return new GuardianSlimeResult(GuardianSlimeType.Magma, MAGMA_CRYSTAL, spot, crystal, count);
    }

    // --- crystals, gauge, groggy

    public static GuardianSlimeResult crystalLayout() {
        int[] v = new int[CRYSTAL_LAYOUT_VALUES.length + 1];
        v[0] = CRYSTAL_LAYOUT;
        System.arraycopy(CRYSTAL_LAYOUT_VALUES, 0, v, 1, CRYSTAL_LAYOUT_VALUES.length);
        return new GuardianSlimeResult(GuardianSlimeType.Crystals, v);
    }

    public static GuardianSlimeResult crystalOrder(int[] order) {
        int[] v = new int[order.length + 2];
        v[0] = CRYSTAL_ORDER;
        v[1] = order.length;
        System.arraycopy(order, 0, v, 2, order.length);
        return new GuardianSlimeResult(GuardianSlimeType.Crystals, v);
    }

    public static GuardianSlimeResult gauge(int value) {
        return new GuardianSlimeResult(GuardianSlimeType.Gauge, value, MAX_GAUGE, MAX_GAUGE);
    }

    /** Damage dealt while the slime is groggy. */
    public static GuardianSlimeResult groggyDamage(long total) {
        GuardianSlimeResult r = new GuardianSlimeResult(GuardianSlimeType.GroggyDamage);
        r.value = total;
        return r;
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(type.getVal());
        for (int v : ints) {
            outPacket.encodeInt(v);
        }
        switch (type) {
            case Wave:
                if (ints[0] == WAVE_CREATE || ints[0] == WAVE_FLAG) {
                    outPacket.encodeByte(flag);
                }
                break;
            case Objects:
                outPacket.encodeInt(tags.length);
                for (int i = 0; i < tags.length; i++) {
                    outPacket.encodeString(tags[i]);
                    outPacket.encodeByte(on[i]);
                }
                break;
            case Magma:
                if (ints[0] == MAGMA_SPOTS) {
                    encodeMagmaSpots(outPacket);
                } else if (ints[0] == MAGMA_LAUNCH) {
                    outPacket.encodeDouble(angle);
                    for (int v : LAUNCH_VALUES) {
                        outPacket.encodeInt(v);
                    }
                    outPacket.encodeDouble(LAUNCH_SCALE);
                }
                break;
            case GroggyDamage:
                outPacket.encodeLong(value);
                break;
        }
    }

    /** Per spot: the pick-up, hit and body boxes (absolute and relative), the spot, then its settings. */
    private void encodeMagmaSpots(OutPacket outPacket) {
        for (int[] s : spots) {
            int id = s[0], ledge = s[1], x = s[2], y = s[3], big = s[4];
            outPacket.encodeByte(1);
            outPacket.encodeInt(id);
            rect(outPacket, x - 90, y - 180, x + 90, y);
            rect(outPacket, x - 70, y - 90, x + 70, y);
            rect(outPacket, -70, -90, 70, 0);
            rect(outPacket, x - 40, y - 80, x + 40, y);
            rect(outPacket, -40, -80, 40, 0);
            outPacket.encodeInt(x);
            outPacket.encodeInt(y);
            for (int v : new int[]{id, ledge, MAGMA_SPOT_DELAY, big, magmaId, 0, magmaId, MAGMA_SPOT_ACTION}) {
                outPacket.encodeInt(v);
            }
        }
    }

    private static void rect(OutPacket outPacket, int l, int t, int r, int b) {
        outPacket.encodeInt(l);
        outPacket.encodeInt(t);
        outPacket.encodeInt(r);
        outPacket.encodeInt(b);
    }
}
