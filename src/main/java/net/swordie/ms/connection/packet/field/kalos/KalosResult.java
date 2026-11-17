package net.swordie.ms.connection.packet.field.kalos;

import net.swordie.ms.connection.OutPacket;

import java.util.List;

/**
 * Kalos the Guardian's field. Structure index: 0 Eye of Penitence (left), 1 Eye of the Abyss (right), 2 Beam Strafe
 * Maneuver (top), 3 Odium Sphere. Constants are the values GMS sends.
 */
public class KalosResult {

    private static final int WILL_ARROW_MS = 2000;
    private static final int WILL_VALUES[] = {20, 100, 100};
    private static final int GENERATOR_TIME = 10000;
    private static final int SPHERE_GENERATOR_RANGE = 1780;
    private static final int ARROW_SPEED = 1500;
    private static final int PENITENCE_LASERS = 2, PENITENCE_RANGE = 100, PENITENCE_ANGLE = 360;
    private static final int ABYSS_ARROWS = 3, ABYSS_RANGE = 500;
    private static final int[] DRONE_SETUP = {330, -1, 4, 150, 10000, 200}; // -1: the mob id goes here
    /** The drone's flight line: left end, top, right end. */
    private static final int[] DRONE_PATH = {-657, -720, -553, 1785, -553};
    private static final int SPHERE_KIND = 4, SPHERE_SPEED = 500, SPHERE_HEIGHT = 720, SPHERE_STAGE = 5;

    private final KalosResultType type;
    private int[] args = new int[0];
    private boolean flag;
    private int[][] spots;
    private List<int[]> list, marks;

    private KalosResult(KalosResultType type, int... args) {
        this.type = type;
        this.args = args;
    }

    // --- setup and timers

    /** The two activation timers, the structures' spots and their flags. */
    public static KalosResult init(int activationMs, int secondMs, int[][] spots, int[] flags) {
        KalosResult r = new KalosResult(KalosResultType.Init, activationMs, secondMs);
        r.spots = spots;
        r.list = List.<int[]>of(flags);
        return r;
    }

    /** Ms until the next structure wakes; the second timer stops while he's bound. */
    public static KalosResult timer(boolean second, int ms) {
        return new KalosResult(second ? KalosResultType.SecondTimer : KalosResultType.Timer, ms);
    }

    /** Chaos/Extreme: three or more structures awake power them all up. */
    public static KalosResult poweredUp(boolean on) {
        KalosResult r = new KalosResult(KalosResultType.PoweredUp);
        r.flag = on;
        return r;
    }

    // --- Kalos's Will and the generators

    public static KalosResult willConfig() {
        return new KalosResult(KalosResultType.WillConfig);
    }

    public static KalosResult beamGenerators(int[][] spots) {
        KalosResult r = new KalosResult(KalosResultType.BeamGenerators);
        r.spots = spots;
        return r;
    }

    public static KalosResult sphereGenerators(int[][] spots) {
        KalosResult r = new KalosResult(KalosResultType.SphereGenerators);
        r.spots = spots;
        return r;
    }

    /** A Will arrow; angle is the client's float bits, passed through. */
    public static KalosResult arrow(int arrowId, int chrId, int angleBits, int x, int y) {
        return new KalosResult(KalosResultType.Arrow, arrowId, chrId, ARROW_SPEED, angleBits, x, y);
    }

    public static KalosResult generatorHit(boolean sphere, int chrId, int generator) {
        return new KalosResult(sphere ? KalosResultType.SphereGeneratorHit : KalosResultType.BeamGeneratorHit,
                chrId, generator);
    }

    // --- structures

    /** Lasers every interval ms; needed = arrow hits to put it down. */
    public static KalosResult penitenceOn(int mobId, int interval, int needed) {
        return new KalosResult(KalosResultType.PenitenceOn, mobId, PENITENCE_LASERS, PENITENCE_RANGE,
                PENITENCE_ANGLE, interval, 0, 0, 0, 0, 0, needed);
    }

    public static KalosResult penitenceOff() {
        return new KalosResult(KalosResultType.PenitenceOff);
    }

    public static KalosResult penitenceLasers(int seq, List<int[]> spots) {
        KalosResult r = new KalosResult(KalosResultType.PenitenceLasers, seq);
        r.list = spots;
        return r;
    }

    /** Arrow hits on an eye so far (0 once it is down). */
    public static KalosResult eyeHits(boolean abyss, int chrId, int hits) {
        return new KalosResult(abyss ? KalosResultType.AbyssHits : KalosResultType.PenitenceHits, chrId, hits);
    }

    /** Three arrows every interval ms. */
    public static KalosResult abyssOn(int mobId, int interval, int needed) {
        return new KalosResult(KalosResultType.AbyssOn, mobId, ABYSS_ARROWS, interval, ABYSS_RANGE, 0, 0, 0, 0,
                needed);
    }

    public static KalosResult abyssOff() {
        return new KalosResult(KalosResultType.AbyssOff);
    }

    /** Per arrow (kind 17/23/29) its target and the spot it marks (powered up only). */
    public static KalosResult abyssVolley(int seq, int[] kinds, int[] targets, List<int[]> marks) {
        KalosResult r = new KalosResult(KalosResultType.AbyssVolley, seq);
        r.spots = new int[][]{kinds, targets};
        r.marks = marks;
        return r;
    }

    public static KalosResult abyssHit(int seq, int chrId) {
        return new KalosResult(KalosResultType.AbyssHit, seq, chrId);
    }

    /** The drone wakes (appear) or flies on from (fromX, fromY) to targetX; needed = passes under a downed generator. */
    public static KalosResult beamDrone(int seq, int mobId, int needed, boolean appear, int fromX, int fromY,
                                        int targetX) {
        KalosResult r = new KalosResult(KalosResultType.BeamDrone, seq, mobId, needed, fromX, fromY, targetX);
        r.flag = appear;
        return r;
    }

    public static KalosResult beamPass(int x, int y) {
        return new KalosResult(KalosResultType.BeamPass, x, y);
    }

    /** Hits so far after a generator did its job; stillOn false once the structure is down. */
    public static KalosResult generatorProgress(boolean sphere, int generator, int hits, boolean stillOn) {
        return new KalosResult(sphere ? KalosResultType.SphereProgress : KalosResultType.BeamProgress, generator,
                hits, stillOn ? 1 : 0);
    }

    /** The Odium Sphere wakes and zigzags along the path. */
    public static KalosResult sphereOn(int mobId, int needed, int[][] path) {
        KalosResult r = new KalosResult(KalosResultType.SphereOn, mobId, needed);
        r.spots = path;
        return r;
    }

    public static KalosResult sphereOff() {
        return new KalosResult(KalosResultType.SphereOff);
    }

    // --- Guardian's Frenzy

    public static KalosResult frenzyStart(List<int[]> domes) {
        KalosResult r = new KalosResult(KalosResultType.FrenzyStart);
        r.list = domes;
        return r;
    }

    /** Survived (a part breaks) or failed. */
    public static KalosResult frenzyEnd(boolean survived) {
        return new KalosResult(survived ? KalosResultType.FrenzySurvived : KalosResultType.FrenzyFailed);
    }

    public void encode(OutPacket outPacket) {
        outPacket.encodeInt(type.getVal());
        outPacket.encodeInt(type.getSub());
        switch (type) {
            case Init:
                encodeInts(outPacket, args);
                outPacket.encodeInt(spots.length);
                for (int i = 0; i < spots.length; i++) {
                    outPacket.encodeInt(i);
                    outPacket.encodeInt(spots[i][0]);
                    outPacket.encodeInt(spots[i][1]);
                }
                int[] flags = list.get(0);
                outPacket.encodeInt(flags.length);
                for (int i = 0; i < flags.length; i++) {
                    outPacket.encodeInt(i);
                    outPacket.encodeByte(flags[i]);
                }
                outPacket.encodeInt(spots.length);
                for (int i = 0; i < spots.length; i++) {
                    outPacket.encodeInt(i);
                    outPacket.encodeByte(0);
                }
                break;
            case PoweredUp:
                outPacket.encodeByte(flag);
                break;
            case WillConfig:
                outPacket.encodeInt(WILL_ARROW_MS);
                encodeInts(outPacket, WILL_VALUES);
                break;
            case BeamGenerators:
                outPacket.encodeInt(GENERATOR_TIME);
                encodePoints(outPacket, spots);
                break;
            case SphereGenerators:
                outPacket.encodeInt(GENERATOR_TIME);
                outPacket.encodeInt(SPHERE_GENERATOR_RANGE);
                encodePoints(outPacket, spots);
                break;
            case PenitenceOn:
            case AbyssOn:
                encodeInts(outPacket, args);
                outPacket.encodeByte(0);
                break;
            case PenitenceLasers:
                encodeInts(outPacket, args);
                encodePoints(outPacket, list.toArray(new int[0][]));
                break;
            case AbyssVolley:
                encodeInts(outPacket, args);
                int[] kinds = spots[0], targets = spots[1];
                outPacket.encodeInt(kinds.length);
                for (int i = 0; i < kinds.length; i++) {
                    outPacket.encodeInt(kinds[i]);
                    outPacket.encodeInt(1);
                    outPacket.encodeInt(targets[i]);
                    int[] mark = marks == null || i >= marks.size() ? null : marks.get(i);
                    outPacket.encodeInt(mark == null ? 0 : 1);
                    if (mark != null) {
                        outPacket.encodeInt(mark[0]);
                        outPacket.encodeInt(mark[1]);
                    }
                }
                break;
            case BeamDrone:
                outPacket.encodeInt(args[0]); // seq
                for (int v : DRONE_SETUP) {
                    outPacket.encodeInt(v < 0 ? args[1] : v);
                }
                for (int i = 0; i < 6; i++) {
                    outPacket.encodeInt(0);
                }
                outPacket.encodeInt(1);
                outPacket.encodeInt(args[2]); // needed
                outPacket.encodeByte(flag);
                outPacket.encodeByte(0);
                outPacket.encodeInt(args[3]);
                outPacket.encodeInt(args[4]);
                outPacket.encodeInt(args[5]);
                encodeInts(outPacket, DRONE_PATH);
                break;
            case SphereOn:
                outPacket.encodeInt(SPHERE_KIND);
                outPacket.encodeByte(0);
                outPacket.encodeByte(1);
                outPacket.encodeInt(SPHERE_SPEED);
                outPacket.encodeInt(SPHERE_HEIGHT);
                outPacket.encodeInt(args[0]); // mob id
                outPacket.encodeInt(SPHERE_STAGE);
                for (int i = 0; i < 4; i++) {
                    outPacket.encodeInt(0);
                }
                outPacket.encodeInt(args[1]); // needed
                encodePoints(outPacket, spots);
                break;
            case FrenzyStart:
                encodePoints(outPacket, list.toArray(new int[0][]));
                break;
            default:
                encodeInts(outPacket, args);
                break;
        }
    }

    private static void encodeInts(OutPacket outPacket, int[] values) {
        for (int v : values) {
            outPacket.encodeInt(v);
        }
    }

    private static void encodePoints(OutPacket outPacket, int[][] points) {
        outPacket.encodeInt(points.length);
        for (int[] p : points) {
            outPacket.encodeInt(p[0]);
            outPacket.encodeInt(p[1]);
        }
    }
}
