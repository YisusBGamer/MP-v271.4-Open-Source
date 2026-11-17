package net.swordie.ms.enums;

import java.util.Arrays;

/**
 * Created on 6/7/2018.
 */
public enum UserEffectType {
    LevelUp(0),
    SkillUse(1),
    SkillUseBySummoned(2),
    Unk3(3), // new
    SkillAffected(4),
    SkillAffected_Ex(5),
    SkillAffected_Select(6),
    SkillSpecialAffected(7),
    Quest(8),
    Pet(9),
    SkillSpecial(10),
    Resist(11),
    ProtectOnDieItemUse(12),
    PlayPortalSE(13),
    JobChanged(14),
    QuestComplete(15),
    IncDecHPEffect(16),
    BuffItemEffect(17),
    SquibEffect(18),
    MonsterBookCardGet(19),
    LotteryUse(20),
    ItemLevelUp(21),
    ItemMaker(22),
    FieldMesoItemConsumed(23), // new
    ExpItemConsumed(24),
    FieldExpItemConsumed(25),
    ReservedEffect(26),
    unk27(27), // old unknown
    UpgradeTombItemUse(28),
    BattlefieldItemUse(29),
    unk30(30), // old unknown
    AvatarOriented(31),
    AvatarOrientedRepeat(32),
    AvatarOrientedMultipleRepeat(33),
    IncubatorUse(34),
    PlaySoundWithMuteBGM(35),
    PlayExclSoundWithDownBGM(36),
    SoulStoneUse(37),
    IncDecHPEffect_EX(38), // correct with 202.3
    IncDecHPRegenEffect(39),
    EffectUOL(40),
    PvPRage(41),
    PvPChampion(42),
    PvPGradeUp(43),
    PvPRevive(44),
    PvPJobEffect(45),
    FadeInOut(46),
    MobSkillHit(47),
    unk48(48), // new
    BlindEffect(49),
    BossShieldCount(50),
    ResetOnStateForOnOffSkill(51),
    JewelCraft(52),
    ConsumeEffect(53),
    PetBuff(54),
    LotteryUIResult(55),
    LeftMonsterNumber(56),
    ReservedEffectRepeat(57),
    RobbinsBomb(58),
    SkillMode(59),
    ActQuestComplete(60),
    Point(61),
    SpeechBalloon(62),
    TextEffect(67),
    SkillPreLoopEnd(68),
    Aiming(69),
    Unk70(70),
    PickUpItem(71), // UI/UIWindow.img/FloatNotice/%d/DrawOrigin
    BattlePvP_IncDecHp(72),
    CatchEffect(73),
    FailCatchEffect(74),
    BiteAttack_ReceiveSuccess(75),
    BiteAttack_ReceiveFail(76),
    IncDecHPEffect_Delayed(77),
    MobSkillSpecial(79), // Papu/Will/JHilla effect
    BlackMageEffect(80), // Black Mage  Effect (100017)
    ResistAbnormalStatus(81),
    Unk82(82),
    RedChat(83),
    StarVortex(84), // HoYoung - Scroll: Star Vortex
    Unk87(87),
    SpeedMirageEff(89), // Bow Master Speed Mirage afterimages (client: 4 skill, 4 char, 4 n, n x (4 x, 4 y, 1 left, 4 i, 4 angle))
    UpgradePotionMsg(101),
    WaterSmashResult(102),
    ElunaJewel(103),
    SomeUpgradeEffectOnUser(104), // Looks like V skill or somehshit
    Unk105(105),
    MoXuanEff(106), // T value: Heir of the Divine / Qi Shield / The Holy Mountain
    ;

    private byte val;

    UserEffectType(int val) {
        this.val = (byte) val;
    }

    public byte getVal() {
        return val;
    }

    public static UserEffectType getByVal(int val) {
        return Arrays.stream(values()).filter(uet -> uet.getVal() == val).findAny().orElse(null);
    }
}
