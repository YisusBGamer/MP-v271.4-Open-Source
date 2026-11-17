package net.swordie.ms.connection.packet.field.kalos;

/**
 * KALOS_FIELD_RESULT (CField_Kalos): int type, int sub. Type 1 = timers and setup, 2 = Guardian's Frenzy (phase 2),
 * 3 = the structures (phase 1), 5 = Kalos's Will arrows and the generators.
 */
public enum KalosResultType {
    Init(1, 1),
    Timer(1, 2),
    SecondTimer(1, 3),
    PoweredUp(1, 8),

    FrenzyStart(2, 31),
    FrenzyFailed(2, 32),
    FrenzySurvived(2, 33),

    BeamPass(3, 9),
    BeamDrone(3, 10),
    BeamProgress(3, 12),
    SphereOff(3, 13),
    SphereOn(3, 14),
    SphereProgress(3, 15),
    PenitenceOff(3, 16),
    PenitenceOn(3, 17),
    PenitenceLasers(3, 18),
    PenitenceHits(3, 19),
    AbyssOff(3, 20),
    AbyssOn(3, 21),
    AbyssVolley(3, 22),
    AbyssHits(3, 23),
    AbyssHit(3, 24),

    WillConfig(5, 25),
    BeamGenerators(5, 26),
    SphereGenerators(5, 27),
    Arrow(5, 28),
    BeamGeneratorHit(5, 29),
    SphereGeneratorHit(5, 30),
    ;

    private final int type;
    private final int sub;

    KalosResultType(int type, int sub) {
        this.type = type;
        this.sub = sub;
    }

    public int getVal() {
        return type;
    }

    public int getSub() {
        return sub;
    }
}
