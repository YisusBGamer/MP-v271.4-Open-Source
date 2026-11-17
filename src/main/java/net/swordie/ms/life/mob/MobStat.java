package net.swordie.ms.life.mob;

import java.util.Comparator;

public enum MobStat implements Comparator<MobStat> {
    IndiePdr(0), // IndiePDR
    IndieMdr(1), // IndieMDR
    IndieAddFinalDamSkill(2),
    IndieSlow(3),
    IndieTriangleFormation(4),
    IndieSixthTriangleFormation(5),
    IndieDummyBuffIcon(6),
    IndieAbyssalDarkness(7),
    IndieTotalDam(8),
    IndieTargetPriority(9),
    IndieEnd(10),
    PAD(11),
    PDR(12),
    MAD(13),
    MDR(14),
    ACC(15),
    EVA(16),
    Speed(17),
    ArcMage2Stack(18),
    Stun(19),
    Freeze(20),
    BeforeFreeze(21),
    Poison(22),
    Seal(23),
    Darkness(24),
    PowerUp(25),
    MagicUp(26),
    PGuardUp(27),
    MGuardUp(28),
    PImmune(29),
    MImmune(30),
    Web(31),
    HardSkin(32),
    Ambush(33),
    Venom(34),
    Blind(35),
    SealSkill(36),
    Dazzle(37),
    PCounter(38),
    MCounter(39),
    RiseByToss(40),
    Weakness(41),
    Showdown(42),
    DevilCry(43),
    MagicCrash(44),
    DamagedElemAttr(45),
    AddDamParty(46), // TotalDamParty
    HitCriDamR(47),
    Fatality(48),
    Lifting(49),
    DeadlyCharge(50),
    Smite(51),
    AddDamSkill(52),
    AddExpSkill(53),
    Incizing(54),
    DodgeBodyAttack(55),
    DebuffHealing(56),
    FinalDmgReceived(57), // AddDamSkill2
    BodyAttack(58),
    TempMoveAbility(59),
    FixDamRBuff(60),
    SpiritGate(61),
    ElementDarkness(62),
    AreaInstallByHit(63),
    BMageDebuff(64),
    JaguarProvoke(65),
    JaguarBleeding(66),
    PinkBeanFlowerPot(67),
    BattlePvPHelenaMark(68),
    BattlePvP_Darklord_Explosion(69),
    PsychicLock(70),
    PsychicLockCoolTime(71),
    PsychicGroundMark(72),
    PowerImmune(73),
    MultiPMDR(74),
    BahamutLightElemAddDam(75),
    UmbralBrand(76), // LefDebuff
    BossPropPlus(77),
    MultiDamSkill(78),
    RWLiftPress(79),
    RWChoppingHammer(80),
    MobAggro(81),
    AreaPDR(82),
    BuffControl(83),
    IgnoreFreeze(84),
    BattlePvP_Ryude_Frozen(85),
    DamR(86),
    ContinuousHeal(87),
    HiddenPullingDebuff(88),
    Curseweaver(89), // CurseTransition
    WindBreakerPinpointPierce(90),
    Transform(91), // Morph
    Suction(92), // MobLock
    LWGathering(93),
    LawOfGravity(94), // KinesisLawOfGravity
    TargetPlus(95),
    ReviveOnce(96),
    BuffFlag(97),
    HolyShell(98),
    ZeroCriticalBind(99),
    Panic(100),
    ReduceFinalDamage(101),
    Stalking(102),
    ChangeMobAction(103),
    HealByDamage(104),
    ActionState(105),
    ShamanCatDot(106),
    KannaAddAttack(107),
    NewPriatePanda(108),
    CatKnitting(109),
    SummonGhost(110),
    TimeBomb(111),
    AddEffect(112),
    Invincible(113),
    Explosion(114),
    Unk115(115),
    HangOver(116),
    LevelInc(117),
    AfterImage(118),
    BurnedInfo(119),
    Sleep(120),
    ExchangeAttack(121),
    ExtraBuffStat(122),
    LinkTeam(123),
    SoulExplosion(124),
    TrueSight(125),
    Laser(126),
    StatResetSkill(127),
    Unk128(128),
    Unk129(129),
    Unk130(130),
    Unk131(131),
    Unk132(132),
    OriginDebuff(133),
    Unk134(134),
    Unk135(135),
    Unk136(136),
    Unk137(137),
    Unk138(138),
    Unk139(139),
    Unk140(140),
    NewBurnedInfo(141),

    // no v265 bit: kept server-side only, never written to a packet
    Mystery(-1),
    DarkLightning(-1),
    ElementResetBySummon(-1),
    Treasure(-1),
    FinalDmgReceived_Shade(-1),
    CradleBlitzkrieg(-1),
    Unk205_86(-1),
    SeperateSoulP(-1),
    SeperateSoulC(-1),
    Ember(-1),

    No(-1),
    ;

    public static final int LENGTH = 5;
    private final int val, pos, bitPos;

    MobStat(int bitPos) {
        this.bitPos = bitPos;
        this.val = bitPos < 0 ? 0 : 1 << (31 - bitPos % 32);
        this.pos = bitPos < 0 ? 0 : bitPos / 32;
    }

    /**
     * Position in the client's n/r/t block (MobStat::DecodeTemporary reads bits 11..118 in bit order), or -1 for
     * stats that only have their own fields after it.
     */
    public int getOrder() {
        return bitPos >= PAD.bitPos && bitPos <= AfterImage.bitPos ? bitPos : -1;
    }

    public boolean isSent() {
        return bitPos >= 0;
    }

    public int getPos() {
        return pos;
    }

    public int getVal() {
        return val;
    }

    /**
     * The client's set (mask constant at 0x147f3c238, tested by 0x140b8d650) plus OriginDebuff, which the reference
     * sends the moving byte for (MOB_STAT_RESET).
     */
    public boolean isMovementAffectingStat() {
        switch (this) {
            case StatResetSkill:
            case Speed:
            case Stun:
            case Freeze:
            case RiseByToss:
            case Lifting:
            case Smite:
            case TempMoveAbility:
            case RWLiftPress:
            case OriginDebuff:
            case IndieSlow:
                return true;
            default:
                return false;
        }
    }

    public int getBitPos() {
        return bitPos;
    }

    public boolean isIndie() {
        return bitPos >= 0 && bitPos < PAD.bitPos;
    }

    public boolean isMobStun() { // MobStat that prevents mob from attacking & moving
        return this.equals(Stun) || this.equals(Freeze);
    }

    public boolean isCrash() { // MobStat that prevents mob from using buffs
        return this.equals(MagicCrash);
    }

    public static MobStat getByBitPos(int bitPos) {
        for (MobStat ms : values()) {
            if (ms.bitPos == bitPos && bitPos >= 0) {
                return ms;
            }
        }
        return null;
    }

    @Override
    public int compare(MobStat o1, MobStat o2) {
        return Integer.compare(o1.bitPos, o2.bitPos);
    }
}
