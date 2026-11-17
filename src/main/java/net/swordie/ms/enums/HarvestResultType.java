package net.swordie.ms.enums;

public enum HarvestResultType {
    Unk0(0),
    Unk1(1),
    HerbalismLevelTooLow(2),
    HerbalismNoSkill(3),
    MiningNoSkill(4),
    NotEnoughFatigue(5),
    TooFarAway(6),
    HarvestingCancelled(7),
    AlreadyInUse(8),
    CannotCollectYet(9),
    UnknownError(10),
    CannotHarvestWhileOnChair(11),
    LevelTooLow(12),

    // v265 client 0x144ec06e0: 16 starts the gather; the others are messages (0x1453da0a0)
    Success(16),
    NoHerbalismTools(17),
    NoMiningTools(18),
    AutoGathering(19);

    private int value;

    HarvestResultType(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }
}
