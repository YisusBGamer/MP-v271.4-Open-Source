package net.swordie.ms.life.mob.skill;

public enum FieldSkillID {

    OrangeTornado(100000),

    BlackMageChainSpearPrepare(100004),
    BlackMageLaser(100005),
    BlackMagePowerOfDestruction(100006),
    LocalLaserAttackByIntervalLaser(100007),    // BlackMageBlackChain
    SummonedSequenceOnAndAttack(100008),        // BlackMageShriekingWall
    BlackMageInvisMob(100009),
    BlackMageMorningStar(100010),
    CenterDirRotationAttack(100011),            // BlackMageBall
    BlacMageTraceBall(100012),                 // BlackMagePiercingGaze
    DynamicObjectOnAndAttack(100013),          // BlackMageCreation
    Monochrome_AreaWarning(100014),            // BlackMageGodlikeLaser
    CreationAreaWarning(100015),               // BlackMageCurseCreationExplosion
    Monochrome_AreaWarning2(100016),           // BlackMageExplosion
    BlackMageCrimsonLightning(100017),

    AkeshiBite(100018),
    LocalLaserAttackByPos(100019),             // AkeshiLaser

    GloomTentacle(100020),
    GloomSummon(100021),
    GloomTerrorFlame(100022),

    SerenCrossLasers(100023),
    SerenMegaAttack(100024),

    // the same ids at other levels
    GuardianSlimeMagmaThrow(100023),
    GuardianSlimeRain(100025),
    HundunDarkCloud(100028),
    KalingSpiritParade(100029),

    ;

    private int id;

    FieldSkillID(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
