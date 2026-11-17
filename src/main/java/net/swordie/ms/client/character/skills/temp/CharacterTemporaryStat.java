package net.swordie.ms.client.character.skills.temp;

import net.swordie.ms.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The server's buff/debuff names. The numbers here are the old v265 bit positions and are NOT what the v271 client
 * gets: v271 buff packets look each stat up by name in resources/v271-cts-local.tsv and v271-cts-remote.tsv (built
 * from the v271 client), see V271LocalStats and V271RemoteStats. A stat missing from those files isn't sent.
 * -1 = used only inside the server.
 */
public enum CharacterTemporaryStat implements Comparator<CharacterTemporaryStat> {
    IndiePAD(0),
    IndieMAD(1),
    IndieDEF(2),
    IndieMHP(3),
    IndieMHPR(4),
    IndieMMP(5),
    IndieMMPR(6),
    IndieACC(7),

    IndieEVA(8),
    IndieJump(9),
    IndieSpeed(10),
    IndieAllStat(11),
    IndieAllStatR(12),
    IndieDodgeCriticalTime(13),
    IndieEXP(14),
    IndieBooster(15),

    IndieFixedDamageR(16),
    PyramidStunBuff(17), // Osiris' Eye: Stuns monsters for 1 second. Includes Pharaoh Yetis and Pharaoh Mummies at a 30% chance. Costs 500 points.
    PyramidFrozenBuff(18), // Horus' Eye: Slows down all monsters for 15 seconds. Includes Pharaoh Yetis and Pharaoh Mummies at a 30% chance. Costs 700 points.
    PyramidFireBuff(19), // Isis' Eye: Does Damage over time to all monsters for 15 seconds every second. Costs 500 points.
    PyramidBonusDamageBuff(20), // Anubis' Eye: Increases 40 attack for 15 seconds. Costs 500 points.
    IndieRelaxEXP(21),
    IndieSTR(22),
    IndieDEX(23),

    IndieINT(24),
    IndieLUK(25),
    IndieDamR(26),
    IndieScriptBuff(27),
    IndieMDF(28),
    IndieAsrR(29),
    IndieTerR(30),
    IndieCr(31),

    IndiePDDR(32),
    IndieCrDmg(33),
    IndieBDR(35),
    IndieStatR(36),
    IndieStance(37),
    IndieIgnoreMobpdpR(38),
    IndieEmpty(39),
    IndiePADR(40),

    IndieMADR(41),
    IndieEVAR(42),
    IndieDrainHP(43),
    IndiePMdR(44), // Final damage
    IndieForceJump(45), // Max Jump (?)
    IndieForceSpeed(46), // Max Speed (?)
    IndieQrPointTerm(47),
    IndieLoopEffect(48),
    IndiePeriodicalSkillActivation(49),
    IndieBuffIcon(52),

    IndieSummon(-1), // Seems to be used by GMS for almost all their summons
    IndieCooltimeReduce(53),
    IndieNotDamaged(54), // Given by Soul Eclipse DW 3rd V & Omega Blaster (Attack mode)  |  Invincibility
    IndieKeyDownTime(55), // DemonicBlast | Twin Blades of Time
    IndieDamReduceR(56),
    IndieCrystalCharge(57),
    IndieNegativeEVAR(58),
    IndieHitDamage(59),
    IndieNoKnockBack(60),
    IndieGrandCross(61), // err38
    IndieHitDamageInclHPR(62), // Indie Hit Damage, including HP% hits
    IndieArcaneForce(63),
    IndieAuthentic(64),
    IndiePowerGuard(65), // Used in Rage (Hero)
    IndieDropPer(66),
    IndieMesoAmountRate(67),
    IndieFloating(68),
    IndieDotHealHP(69), // T's name; was IndieRocketBooster (gap-paired, unused)
    IndieInvincibleBelief(70), // used in Warrior Explorer Link Skill
    IndieUnk212_63(71),
    IndieInfiniteMDF(72), // infiniteMDF  |  Used in Time Holding (Zero)
    // Somewhere there be 4 new ones
    IndieProtectiveShield(73), // used in Guardian Embrace
    IndieUNK218_2(74),
    IndieNBDR(75), // Normal Mob Dmg
    IndieAntiMagicShell(78),
    IndieUNK218_4(-1),
    IndieUNK3(-1),
    IndiePartyExp(83),
    IndieUNK5(84),
    IndiePartyDrop(85),
    IndieUnk63(86),
    IndieCalcDamageStat(-1), // v271 index 88 only (v271-cts-local.tsv)
    IndieAutoChargeStack(-1), // v271 index 90 only: charges of autoChargeStack skills
    IndieStatCount(87),
    PAD(88),
    DEF(89),
    MAD(90),
    ACC(91),
    EVA(92),
    Craft(93),

    Speed(94),
    Jump(95),
    MagicGuard(96),
    DarkSight(97),
    Booster(98),
    PowerGuard(99),
    MaxHP(100),
    MaxMP(101),

    Invincible(102),
    SoulArrow(103),
    Stun(104),
    Poison(105),
    Seal(106),
    Darkness(107),
    ComboCounter(108),
    WorldReaver(-1),
    HammerOfTheRighteous(109),
    BigHammerOfTheRighteous(110),
    WeaponCharge(111),

    HolySymbol(112),
    MesoUp(113),
    ShadowPartner(114),
    ThiefSteal(115),
    PickPocket(116),
    MesoGuard(117),

    Thaw(118),
    Weakness(119),
    Curse(120),
    Slow(121),
    Morph(122),
    Regen(123),
    BasicStatUp(124), // Maple warrior cts
    Stance(125), // given with ShadowStitch

    SharpEyes(126),
    ManaReflection(127),
    Attract(128),
    NoBulletConsume(129),
    Infinity(130),
    AdvancedBless(131),
    IllusionStep(132),
    Blind(133),

    Concentration(134),
    BanMap(135),
    MaxLevelBuff(136),
    MesoUpByItem(137),
    Ghost(140),
    Barrier(141),

    UnionWealth(-1),
    RuneOfGreed(-1),
    ReverseInput(142),
    ItemUpByItem(143),
    RespectPImmune(144),
    RespectMImmune(145),
    DefenseAtt(146),
    DefenseState(147),
    DojangBerserk(148),
    DojangInvincible(149),

    DojangShield(150),
    SoulMasterFinal(151),
    WindBreakerFinal(152),
    ElementalReset(155),
    HideAttack(156),
    EventRate(157),
    ComboAbilityBuff(158),
    ComboDrain(159),

    ComboBarrier(-1),
    BodyPressure(-1), // nOpt = slv
    RepeatEffect(160),
    ExpBuffRate(161),
    StopPortion(162),
    StopMotion(163),
    Fear(164),
    HiddenPieceOn(165),

    MagicShield(166),
    MagicResistance(167),
    SoulStone(168),
    Flying(169),
    Frozen(170),
    AssistCharge(171),
    Enrage(172),
    DrawBack(173),

    NotDamaged(174),
    FinalCut(175),
    HowlingAttackDamage(176),
    BeastFormDamageUp(177),
    Dance(178),
    EMHP(179),
    EMMP(180),
    EPAD(181),

    EMAD(182),
    EPDD(183),
    Guard(184),
    Cyclone(185),
    RadiantOrb(186),

    // 1 2 of these are removed, idk which
    Conversion(-1),
    Revive(-1), //Invis frame after revive
    Mechanic(190),
    BeastFormMaxHP(191),
    Dice(192),

    BlessingArmor(193),
    DamR(194),
    TeleportMasteryOn(195),
    CombatOrders(196),
    Beholder(197),
    DispelItemOption(198),
    Inflation(199), //  |  80001757 | 80001869
    OnixDivineProtection(200),

    Web(201),
    UserBomb(-1), // Mob Skill 171 (UserBomb)
    Bless(202),
    DisOrder(204),
    Thread(205),
    Team(206),
    Explosion(207),
    BuffLimit(208),

    STR(209),
    INT(210),
    DEX(211),
    LUK(212),
    DispelItemOptionByField(213),
    DarkTornado(214),
    PVPDamage(215),
    PvPScoreBonus(216), // Stopped incrementing by 1

    PvPInvincible(217),
    PvPRaceEffect(218),
    WeaknessMdamage(219),
    Frozen2(220),
    PVPDamageSkill(221),
    AmplifyDamage(222),
    Shock(223),
    InfinityForce(224),

    IncMaxHP(225),
    IncMaxMP(226),
    HolyMagicShell(227),
    KeyDownTimeIgnore(228),
    ArcaneAim(229),
    MasterMagicOn(230),
    AsrR(231),
    TerR(232),

    DamAbsorbShield(233),
    DevilishPower(234),
    Roulette(236),
    SpiritLink(238),
    AsrRByItem(239),
    Event(240),
    CriticalBuff(241),
    DropRate(242),

    PlusExpRate(243),
    ItemInvincible(244),
    Awake(245),
    ItemCritical(246),
    Event2(248),
    DDR(250),
    VampiricTouch(249),
    IncCriticalDam(-1),

    IncTerR(251),
    IncAsrR(252),
    DeathMark(253),
    UsefulAdvancedBless(254),
    Lapidification(255),
    VenomSnake(256),
    CarnivalAttack(257),
    CarnivalDefence(258),

    CarnivalExp(259),
    SlowAttack(260),
    PyramidEffect(261),
    KillingPoint(-1),
    HollowPointBullet(262),
    KeyDownMoving(263),
    IgnoreTargetDEF(264),
    ReviveOnce(267),

    Invisible(268),
    EnrageCr(269),
    EnrageCrDamMin(270),
    Judgement(271),
    DojangLuckyBonus(272),
    PainMark(273),
    Magnet(274),
    MagnetArea(275),

    GuidedArrow(276),
    SpiritFlow(-1),
    LucentBrand(-1),
    ExtraSkillCTS(-1),
    Unk199_256(-1),
    TideOfBattle(-1),
    GrandGuardian(-1),
    Unk227_269(-1),
    Unk227_270(-1),
    UnionLuck(-1),
    VampDeath(290),
    BlessingArmorIncPAD(291),
    KeyDownAreaMoving(292),
    Larkness(293),
    StackBuff(294),
    BlessOfDarkness(-1),

    AntiMagicShell(295),
    AntiMagicShellEx(-1),

    LifeTidal(296),
    HitCriDamR(297),
    SmashStack(298),
    PartyBarrier(299),
    ReshuffleSwitch(300),
    SpecialAction(301),
    VampDeathSummon(302),

    StopForceAtomInfo(303),
    SoulGazeCriDamR(304),
    SoulRageCount(305),
    PowerTransferGauge(306),
    AffinitySlug(307),
    Trinity(308),
    IncMaxDamage(309),
    BossShield(310),

    MobZoneState(311),
    GiveMeHeal(312),
    TouchMe(313),
    Contagion(314),
    ComboUnlimited(-1),
    SoulExalt(315),
    IgnorePCounter(-1),
    IgnoreAllCounter(316),

    IgnorePImmune(317),
    IgnoreAllImmune(318),
    FinalJudgement(320),
    FireAura(321),
    VengeanceOfAngel(322),
    HeavensDoor(323),

    Preparation(324),
    BullsEye(325),
    IncEffectHPPotion(326),
    IncEffectMPPotion(327),
    BleedingToxin(328),
    IgnoreMobDamR(329),
    Asura(330),
    OmegaBlaster(331),
    FlipTheCoin(332),

    UnityOfPower(333),
    Stimulate(334),
    ReturnTeleport(335),
    DropRIncrease(336),
    IgnoreMobpdpR(337),
    BdR(338),
    CapDebuff(339),

    Exceed(340),
    DiabolikRecovery(341),
    FinalAttackProp(343),
    ExceedOverload(344),
    OverloadCount(345),
    BuckShot(346),
    FireBomb(347),
    HalfstatByDebuff(348),

    SurplusSupply(349),
    SetBaseDamage(350),
    EVAR(351),
    NewFlying(352),
    AmaranthGenerator(353),
    OnCapsule(-1),
    CygnusElementSkill(354),
    StrikerHyperElectric(355),

    EventPointAbsorb(356),
    EventAssemble(357),
    StormBringer(358),
    ACCR(359),
    DEXR(360),
    Albatross(-1),
    Translucence(361),
    PoseType(362),

    LightOfSpirit(363),
    ElementSoul(364),
    CosmicOrb(365),
    GlimmeringTime(366),
    TrueSight(367),
    SoulExplosion(368),
    SoulMP(369),
    FullSoulMP(370),
    SoulSkillDamageUp(371),

    ElementalCharge(372),
    Restoration(373),
    CrossOverChain(374),
    ChargeBuff(-1),
    Reincarnation(375),
    Unk227_361(-1),
    ChillingStep(378),
    DotBasedBuff(379),
    SixthDotBasedBuff(380),

    BlessEnsenble(381),
    ComboCostInc(382),
    ExtremeArchery(-1),
    NaviFlying(383),
    QuiverCatridge(385),
    ExtraQuiverCatridge(386),
    ExtraQuiverMagic(387), // special arrowheads loaded: n
    ExtraQuiverVamp(388),
    AdvancedQuiver(-1),
    UserControlMob(389),
    ImmuneBarrier(390),

    ArmorPiercing(-1),
    CriticalGrowing(391),
    RelicEmblem(392),
    QuickDraw(393),
    BowMasterConcentration(394),
    TimeFastABuff(396),
    TimeFastBBuff(397),

    GatherDropR(399),
    AimBox2D(400),
    PerfectShot(401),
    StatusResistance(402),
    IncMonsterBattleCaptureRate(403),
    DotHealHPPerSecond(404),
    DotHealMPPerSecond(405),
    SpiritGuard(406),
    PreReviveOnce(408),

    SetBaseDamageByBuff(409),
    LimitMP(410),
    ReflectDamR(411),
    ComboTempest(412),
    MHPCutR(413),
    MMPCutR(414),
    SelfWeakness(415),
    ElementDarkness(416),

    FlareTrick(417),
    Ember(418),
    Dominion(419),
    SiphonVitality(420),
    DarknessAscension(421),
    BossWaitingLinesBuff(422),
    DamageReduce(423),
    ShadowServant(424),

    ShadowIllusion(425),
    KnockBack(426),
    AddAttackCount(427),
    ComplusionSlant(428),
    JaguarSummoned(429),
    JaguarCount(430),

    SSFShootingAttack(431),
    DevilCry(432),
    ShieldAttack(433),
    DarkLighting(434),
    AttackCountX(435),
    BMageDeath(436),
    BombTime(437),

    NoDebuff(438),
    BattlePvPMikeShield(439),
    BattlePvPMikeBugle(440),
    XenonAegisSystem(441),
    AngelicBursterSoulSeeker(442),
    HiddenPossession(443),
    NightWalkerBat(444),
    NightLordMark(445),

    WizardIgnite(446),
    FireBarrier(447),
    ChangeFoxMan(448),
    DivineEcho(449),
    DemonFrenzy(450),
    ShadowSpear(451),
    MastemasMark(452),
    RiftOfDamnation(453),

    QuiverBarrage(454),
    SwordsOfConsciousness(455),
    PrimalGrenade(456),
    Unk200_430(-1),
    Unk200_431(-1),
    BattlePvPLangEProtection(464),
    BattlePvPLeeMalNyunScaleUp(465),
    BattlePvPRevive(466),
    BattlePvPHelenaMark(461),
    BattlePvPHelenaWindSpirit(463),
    PinkbeanAttackBuff(467),
    PinkbeanRelax(468),
    PinkbeanRollingGrade(469),

    PinkbeanYoYoStack(470),
    RandAreaAttack(471),
    Unk200_442(-1),
    NextAttackEnhance(474),
    //    AranBeyonderDamAbsorb(447), // between NextAttackEnhance and RoyalGuardState: 1 removed
    AranCombotempastOption(476),
    NautilusFinalAttack(-1),
    ViperTimeLeap(477),
    RoyalGuardState(478),

    RoyalGuardPrepare(479),
    MichaelSoulLink(480),
    MichaelStanceLink(481),
    TriflingWhimOnOff(482),
    AddRangeOnOff(483),
    KinesisPsychicPoint(484),
    KinesisPsychicOver(485),
    KinesisPsychicShield(-1),

    KinesisIncMastery(486),
    KinesisPsychicEnergeShield(487),
    BladeStance(488),
    DebuffActiveSkillHPCon(489),
    DebuffIncHP(490),
    BowMasterMortalBlow(491),
    AngelicBursterSoulResonance(492),
    Fever(493),

    IgnisRore(494),
    //    RpSiksin(459), somewhere between 436 and 459 one is removed
    TeleportMasteryRange(495),
    FixCoolTime(496),
    IncMobRateDummy(497),
    AdrenalinBoost(498),
    AranSmashSwing(-1),
    AranDrain(499),

    AranBoostEndHunt(500),
    HiddenHyperLinkMaximization(-1),
    RWCylinder(501),
    RWCombination(502),
    Unk188_460(503),
    RWMagnumBlow(504),
    RWBarrier(505),
    RWBarrierHeal(506),

    RWMaximizeCannon(507),
    RWOverHeat(508),
    UsingScouter(509),
    RWMovingEvar(510),
    Stigma(511),
    MahasFury(512),
    HeavensDoorCooltime(513),
    SkillCooltime(-1),

    SealedRuneCooltime(514),
    PinPointSalvo(515),
    MeltDown(516),
    SparkleBurstStage(517),
    LightningCascade(518),
    BulletParty(519),
    LoadedDice(520),

    Benediction(521),
    Unk221_497(522),
    VoidStrike(523),
    Unk212_504(524),

    WeaponAura(525),
    ManaOverload(527),
    RhoAias(528),
    PsychicTornado(529),

    SpreadThrow(530),
    WindEnergy(531),
    StackOverTimeSkill(532), // T's CannonShooter_BFCannonBall: V-skill stack counts (Cannoneer, Buccaneer, Mo Xuan, Lynn...)
    ShadowAssault(535),
    Unk188_492(536),
    Unk188_494(537),
    Unk216_516(538),
    BlitzShield(539),

    SplitShot(540),
    FreudBlessing(541),
    OverloadMode(542),
    SpotLight(543),
    SylvidiaFlight(544),
    MuscleMemory(545),
    WingsOfGlory(546),
    TrickBladeMobStat(547),
    Overdrive(548),
    EtherealForm(549),
    LastResort(550),
    ViciousShot(-1),
    CurseOfCreation(553),
    CurseOfDestruction(554),
    BlackMageDebuff(555),
    ImpenetrableSkin(-1),

    PapulatusTimeLock(-1),
    Unk199_521(-1),
    Unk199_521Ex(-1),
    Resonance(-1),
    FlashCrystalBattery(-1),
    Unk199_524(-1),
    CrystalBattery(-1),
    Deus(-1),
    CrystalChargeMax(-1),
    BattlePvPRyudeSword(-1),
    BattlePvPAliciaBless(-1),
    Unk199_530(-1),
    Unk200_557(-1),
    SpecterEnergy(569),
    SpecterState(570),
    BasicCast(571),
    ScarletCast(572),
    GustCast(573),
    AbyssalCast(574),
    ImpendingDeath(575),
    BattleFrenzy(580),
    ChargeSpellAmplifier(581),
    InfinitySpell(582),
    ConversionOverdrive(583),
    Solus(-1),
    AbyssalRecall(576),
    SixthPhoenix(577),
    HolyAdvent(578),
    Unk199_544(-1),
    WillPoison(587),
    Unk199_546(588),
    BigBangAttackCharge(589),
    HolyMagicShellCooltime(590),
    TridentStrike(591),
    ComboInstinct(592),
    GaleBarrier(597),
    InfernoSphere(598),
    SwordOfLight(599),
    PhantomMark(600),
    PhantomMarkMobStat(601),
    Unk200_568(-1),
    Unk200_569(-1),
    Unk200_570(-1),
    KeyDownCTS(-1),
    Unk200_572(-1),
    Unk200_573(-1),
    PMdReduce(608),
    ForbidOpPotion(609),
    ForbidEquipChange(610),
    TanadianRuin(611),
    AeonianRise(612),
    Unk202_579(-1),
    BattlePvPLangEClaw(615),
    BattlePvPMikeProtection(616),
    ProtectiveShield(617),
    DemonicBlast(-1),
    AncientGuidance(618),
    BattlePvPWonkyAttack(-1),
    BattlePvPWonkyHeal(-1),
    BattlePvPWonkyProtection(-1),
    Unk205_590(-1),
    Unk205_591(-1),
    Unk205_592(-1),
    BlessingVSkill(-1),
    Unk205_593(-1),
    Unk218_590(-1),
    Unk208_594(-1),
    Unk208_595(-1),
    Unk208_596(-1),
    Unk208_597(-1),
    Unk228_634(-1),
    Unk228_635(-1),
    Unk228_636(-1),
    TreasureHunt(-1), // *
    AT_Attributes(639),
    AT_Energy(640),
    AT_Clone(641),
    AT_HyperClone(642),
    AT_ButterflyDream(643),
    AT_MasterElixir(-1),
    AT_GodsComing(-1),
    EmpiricalKnowledge(-1),
    Unk209_609(-1),
    AncientProtectionShield(-1),
    Unk209_611(-1),
    AT_DreamShangriLa(-1),
    DracoSurgeBypassCounter(-1),
    LW_Ether(655),
    LW_Creation(656),
    LW_Dike(657),
    LW_Wonder(658),
    LW_Restore(659),
    LW_Nobility(660),
    LW_Resonance(661),
    RuneOfPurification(662),
    Unk214_624(663),
    DuskDarkness(664),

    // Party Auras
    HastyAura(665),
    DrainingAura(666),
    BlueAura(667),
    DarkAura(668),
    WeakeningAura(669),
    AuraScythe(670),
    IceAura(671),
    ParashockGuard(672),
    DivineForce(673),
    DivineSpeed(-1),
    IncarnationAura(674),

    WrathOfGods(675), // *
    Revenant(676),
    RevenantDamage(677),
    SilhouetteMirage(678),
    BlizzardTempest(679),
    PhotonRay(680),
    AbyssalLightning(681),
    LightningSpearMultistrike(682),
    RoyalKnights(683),
    SalamanderMischief(685),
    LawOfGravity(686),
    RepeatingCrossbowCartridge(687),
    CrystalGate(688),
    ThrowBlasting(689),
    SageThreePathsApparition(690),
    DarknessAura(691),
    MuscleMemoryFinale(692),
    LiberationOrbPassive(693),

    LiberationOrb(694),
    EgoWeaponAlpha(695),
    RelicUnbound(696),
    MoraleBoost(-1),
    AfterImageShock(697),
    Malice(698),
    Possession(699),
    DeathBlessing(700),
    ThanatosDescent(701),
    RemainIncense(703),
    GripOfAgony(-1),
    DragonFang(-1),
    SerenDebuff1(-1), // *
    SerenDebuff2(-1),
    SerenDebuff3(-1),
    PriorPreparation(-1),
    Unk223_666(-1),
    Unk223_667(-1),
    Unk223_668(-1),
    Unk223_669(-1),
    Unk223_670(-1),
    Unk227_674(-1),
    Unk227_675(-1),
    Unk227_676(-1),
    Unk227_677(-1),
    Unk227_678(-1),
    Unk227_679(-1),
    Unk228_682(-1),
    Unk227_680(-1), // *
    DragonVeinReading(726),
    MountainSeeds(727),
    PeerlessMountain(728),
    AbsorptionWater(729), // Absorbing Water
    AbsorptionWind(730), // Absorbing Wind
    AbsorptionSun(731), // Absorbing Sun
    UnconstrainedDragonVein(732), // DragonVein
    Unk228_691(733),
    Unk228_692(734),
    NatureFriend(735),
    DragonVeinTrace(736),
    StormWhim(738),
    // v265 classes, T's bit positions
    Holding(384),
    SeaSerpent(739),
    SerpentStone(740),
    SerpentScrew(741),
    FlameSweep(743),
    Cosmos(744),
    SixthAngelsRay(758),
    TranscendentLight(766), // Transcendent's Light Blessing
    SixthAssassination(767),
    SixthCovertShadowBuff(769),
    ArtificialEvolution(770),
    SixthFrozenLightning(771),
    SacredBastion(820),
    RenPlumSwordForm3Stack(829),
    RenPlumSwordForm3Shoot(830),
    Sublimation(831),
    RenPlumSwordEx(832),
    SpiritAwakening(833),
    SpiritAwakeningStack(834),
    AuthenticDarkness(835),
    MukHyunDivine(941),
    MukHyun_SEON_PUNG_GAG(943),
    MukHyun_HO_SIN_GANG_GI(945),
    Unk228_695(-1),
    Unk228_696(-1),
    Unk223_671(-1), // *
    Unk223_672(-1),
    Unk223_673(-1),

    WillowDodge(-1),
    Unk176_471(-1),
    HayatoStance(842),
    HayatoStanceBonus(-1),
    EyeForEye(844),
    HayatoPAD(843),
    Unk218_662(-1),
    Unk218_663(-1),
    HayatoHPR(-1),
    HayatoMPR(-1),
    HayatoBooster(850),
    Unk176_476(-1),
    Unk176_477(-1),
    Jinsoku(-1),

    HayatoCr(-1),
    HakuBlessing(-1),
    HayatoBoss(-1),
    BattoujutsuAdvance(-1),
    Unk176_483(-1),
    Unk176_484(-1),
    BlackHeartedCurse(-1),

    BeastMode(-1),
    TeamRoar(-1),
    Unk176_488(-1),
    Unk176_489(-1),
    Unk176_490(-1),
    Unk176_491(-1),
    Unk176_492(-1),
    Unk176_493(-1),
    WaterSplashEventMarking(-1),

    WaterSplashEventMarking2(-1),
    WaterSplashEventCombo(-1),
    WaterSplashEventWaterDripping(-1),
    Unk188_539(-1),
    YukiMusumeShoukan(-1),
    IaijutsuBlade(-1),
    Unk199_594(-1),
    Unk199_595(-1),
    Unk199_596(-1),
    Unk199_597(-1),
    BroAttack(-1),
    LiberatedSpiritCircle(-1),
    Unk205_639(-1),
    ShikigamiDoppelganger(-1),
    Unk208_648(-1),
    CritterCrossing(-1),
    Unk218_703(-1),
    Unk218_704(-1),
    Unk221_709(-1),
    Unk218_705(-1),
    YakshaGreatLord(-1),
    YakshaGreatOniLord(-1),
    Unk218_708(-1),
    Unk218_709(-1),
    Unk227_739(-1),

    // v265 stats, bit numbers from the v265 reference
    HeroAuraBladeStack(593),
    HeroAuraBladeAdv(594),
    HeroSongOfBlade(595),
    CrusaderPanic(752),
    FlashSlashStack(804),
    // Mechanic Robo Conversion: CB-P1 (r 35141011) and the three parts on its nodebot; the client's nodebot level
    // is the sum of the parts' n. No local tails; the parts are read remotely as short + int, the toggle is not.
    RobotTransition(805),
    RobotTransitionRoboLuncherRM7(806),
    RobotTransitionMagneticField(807),
    RobotTransitionRoboFactory(808),
    GrandCross(286),
    ReincarnationMission(376),
    ReincarnationOnOff(377),
    HolyWater(754),
    // High Mountain: Mitra's Authority (n; the client wants r set before it spends any) and the middle boss barrier.
    // No local tails, not read remotely.
    EpicDungeonGauge(755),
    EpicDungeonMiddleBossBarrier(756),
    TriumphFeather(757),
    HolyBlood(762),
    ElementFlameBuff(395),
    OrbitalExplosion(763),
    DragonSlave(764),
    FlameOrbMultiAttack(799),
    EnhanceInfernalBreath(823),
    SelfHyperBodyMaxHP(848),
    BossDamageRate(856),
    KeyDownEnable(652),
    LefBuffMastery(278),
    TempSecondaryStat(279), // a skill's stack count (Superstar / Triple Color Spotlight); local tail int, remote short + int
    HarmonyLink(559),
    LefFastCharge(560),
    CrystalChargeBuffIcon(562),
    DslayerMetamorphosis(826),
    Nightmare(816),
    RapidFire(818), // HEXA Rapid Fire: n hits toward reload, x 1 = Shootout Mode; tail x
    IceAuraZone(753),
    Warrior_AuraWeaponStack(526),
    SixthResonate(776),
    SummonChakri(775),
    KhaliUltimatum(596),
    DeceivingBlade(781),
    Oblivion(551),
    LarknessStop(774),
    ElementalKnight(773),
    EnhanceQuadrupleThrow(749),
    EnhanceNightLordFourSeasons(777),
    DarknessShuriken(778),
    SixthStormArrowEx(800),
    ShadowAcceleration(779),
    PathFinderForceCompulsion(918), // Curse Enchant Force: n = the curse picked
    ShadowBurst(780),
    SixthAssassinationDarkSight(768),
    StrikerChainReaction(710),
    PhantomDefyingFate(684),
    NaturesBelief(809), // Wild Hunter Nature's Truth: Soul Resonance
    LamourCarte(810),
    Joker(811),
    AdrenalinActivate(718),
    AdrenalinMaximum(812),
    LPBattleMode(584),
    HitStackDamR(556),
    FifthGoddessBless(630),
    PinkbeanMatryoshka(632),

    // Yeti, bit numbers from the v265 reference; read order and tails checked in the client
    YetiCook(287), // Yeti Master Chef; tail x (0 shows the halved EXP), b
    YetiFuryGauge(720), // tail x = rage; the client checks Yeti Rage's cost against it
    YetiFuryMode(721), // Enraged; tail x >= 1 while Furious Roar is still usable
    YetiMushroom(722), // Spice Overload (the client looks for r 135001017)
    YetiMyFriendPepe(723), // Hey Pepe!

    // v265 stats, bit numbers from the v265 reference
    EvasionMaster(845),
    Siksin(846),
    SelfHyperBodyIncPAD(847),
    SelfHyperBodyMaxMP(849),
    EvasionUpgrade(853),
    CriticalBuffAdd(854),
    FoxBless(855),
    KenjiShadowAttackBuff(875),
    SkillDeployment(857),
    AntiEvilShield(860), // Kanna Circle of Suppression
    SummonProp(880),
    MitsuhideDebuff(886), // Akechi's Provoke / Overwhelm (MobSkill 261 lv 3 / 4): n = x, extra int = y
    ShamanMode(861),

    // v265 stats, bit numbers from the v265 reference
    FlashMirage(761),
    GrandFinale(759),
    WarInTheShade(760),

    // v265 stats, bit numbers from the v265 reference
    EnhancePiercing(745),
    EnhanceSniping(746),
    UltimateSniping(747),
    UltimatePiercing(748),
    BlasterFileBunker(921),

    // v265 stats, bit numbers from the v265 reference
    CannonShooter_MiniCannonBall(533),
    CannonShooter_RollingCannon(534),

    // v265 stats, bit numbers from the v265 reference
    KarmaBlade(154),
    Mortality(153),

    // v265 stats, bit numbers from the v265 reference
    SixthJavelinStack(282),
    SixthGloryWingJavelinStack(283),
    SixthCrystalStack(284),
    ShadowMomentum(285), // Night Walker inertia stacks
    NAAnnihilation(702),
    NAOminousStream(704),
    NABrutalPang(705),
    NALinkSkill(709),
    SixthHakuman(899), // Kanna Hakumenkonmou Blessing
    SixthShinBatto(900), // Jin Quick Draw stance
    MarkingStella(902),
    RabbitStack(903),
    ShineMageStarlight(904),
    ShineMageAppear(905),
    ConstelEagle(906),
    ShineMageWitchHat(907),
    ConstelHornedWhelk(908),
    ShineMageTrophy(909),
    ShineMageShine(910),
    ShineMageButterfly(911),
    ShineMageWolf(912),
    ShineMageWolfHowling(913),
    ShineMageCapella(914),
    ShineMageHourglass(915),
    ShineMageCircleOfSaviour(916),

    // v265 stats, bit numbers from the v265 reference
    TimeBomb(203),

    // Twostate
    EnergyCharged(-1),
    DashSpeed(928),
    DashJump(929),
    RideVehicle(930),
    PartyBooster(931),
    GuidedBullet(932),
    Undead(933),
    RideVehicleExpire(934),
    RelicGauge(935),
    Grave(-1),
    // v271-only client stats (local packets only, v271-cts-local.tsv)
    GlacialFury(-1),
    KinesisTeleport(-1),
    KinesisPsychicBoard(-1),
    ReleaseFrenzy(-1),
    Jaguar(-1),
    IndieEventForSkill(-1), // v271 indie index 86 (Princess Sakuno's Blessing in GMS)
    MukhyunLinkSkill(-1), // v271 index 219
    Hekate(-1), // v271 index 656 (Sol Hecate)
    HekateStyx(-1), // v271 index 657
    SwayingBranch(-1), // Lara (jobs 16001, 162xx): damage x(100 + n)/100
    PredatorsLeap(-1), // Footstool 33001007 is usable while n > 0 (once per set)
    WHOverbite(-1), // Overbite 400031076 counter (n)
    LuminousMemorize(-1), // Equalize 27121054; its end also ends the enhanced Aether Conduit
    Anemoi(-1), // Wind Archer Anemoi 13141008: while n != 0 the client keys down r unless its 13141008 summon is up
    ShineWarriorEternalGuardian(-1), // Erel Light Eternal Guardian (n stacks, r 400011169); one int after the Indie section
    ShineWarriorPathOfFionn(-1), // Erel Light Path of Fionn: n = follow-up uses left (r = the last cast id)
    ShineWarriorSixthSpearOfLugh(-1), // Erel Light SHINE Spear of Lugh: n = stacks (8 = Stellar Strike ready)
    ShineHexaTreeOfStarlight(-1), // SHINE Tree of Stars (500001143-146), n 1 for the buff's time
    ShineRuneAttack(-1), // Rune of Contact 80002889: while on, the client fires its beams (extra skill) by itself
    Sneak(-1), // Infiltrate (the beginner hiding skill): GMS sends this, not DarkSight

    None(-1),

    ;

    private final int bitPos;
    private final int val;
    private final int pos;

    public static final int length = 33; // 132
    private static final Logger log = LogManager.getRootLogger();


    public static final List<CharacterTemporaryStat> ORDER = Arrays.asList(
            MukHyunDivine, MukHyun_SEON_PUNG_GAG, MukHyun_HO_SIN_GANG_GI, STR, INT, DEX, LUK, PAD,
            DEF, MAD, ACC, EVA, EVAR, Craft, Speed, Jump,
            EMHP, EMMP, EPAD, EMAD, EPDD, MagicGuard, DarkSight, Booster,
            PowerGuard, Guard, MaxHP, MaxMP, Invincible, SoulArrow, Stun, Shock,
            Poison, Seal, Darkness, ComboCounter, HammerOfTheRighteous, BigHammerOfTheRighteous, WeaponCharge, ElementalCharge,
            HolySymbol, MesoUp, ShadowPartner, ThiefSteal, PickPocket, MesoGuard, Thaw, Weakness,
            WeaknessMdamage, Curse, Slow, TimeBomb, BuffLimit, Team, DisOrder, Thread,
            Morph, Ghost, Regen, BasicStatUp, Stance, SharpEyes, ManaReflection, Attract,
            Magnet, MagnetArea, GuidedArrow, LefBuffMastery, TempSecondaryStat, ShadowMomentum, NoBulletConsume, StackBuff, Trinity, Infinity,
            AdvancedBless, IllusionStep, Blind, Concentration, BanMap, MaxLevelBuff, Barrier, DojangShield,
            ReverseInput, MesoUpByItem, ItemUpByItem, RespectPImmune, RespectMImmune, DefenseAtt, DefenseState, DojangBerserk,
            DojangInvincible, SoulMasterFinal, WindBreakerFinal, Mortality, KarmaBlade, ElementalReset, HideAttack, EventRate,
            ComboAbilityBuff, ComboDrain, PartyBarrier, RepeatEffect, ExpBuffRate, StopPortion, StopMotion, Fear,
            MagicShield, MagicResistance, SoulStone, Flying, NewFlying, NaviFlying, Holding, Frozen,
            Frozen2, Web, Enrage, NotDamaged, FinalCut, HowlingAttackDamage, BeastFormDamageUp, Dance,
            Mechanic, DrawBack, BeastFormMaxHP, Dice, BlessingArmor, BlessingArmorIncPAD, DamR, TeleportMasteryOn,
            CombatOrders, Beholder, DispelItemOption, DispelItemOptionByField, Inflation, OnixDivineProtection, Bless, Explosion,
            DarkTornado, IncMaxHP, IncMaxMP, PVPDamage, PVPDamageSkill, PvPScoreBonus, PvPInvincible, PvPRaceEffect,
            HolyMagicShell, InfinityForce, AmplifyDamage, KeyDownTimeIgnore, MasterMagicOn, AsrR, AsrRByItem, TerR,
            DamAbsorbShield, Roulette, Event, SpiritLink, CriticalBuff, DropRate, PlusExpRate, ItemInvincible,
            ItemCritical, Event2, VampiricTouch, DDR, IncTerR, IncAsrR, DeathMark, PainMark,
            UsefulAdvancedBless, Lapidification, VampDeath, VampDeathSummon, VenomSnake, CarnivalAttack, CarnivalDefence, CarnivalExp,
            SlowAttack, PyramidEffect, HollowPointBullet, KeyDownMoving, KeyDownAreaMoving, CygnusElementSkill, IgnoreTargetDEF, Invisible,
            ReviveOnce, AntiMagicShell, EnrageCr, EnrageCrDamMin, LifeTidal, Judgement, DojangLuckyBonus, HitCriDamR,
            Larkness, SmashStack, ReshuffleSwitch, SpecialAction, ArcaneAim, StopForceAtomInfo, SoulGazeCriDamR, SoulRageCount,
            PowerTransferGauge, AffinitySlug, SoulExalt, HiddenPieceOn, BossShield, MobZoneState, GiveMeHeal, TouchMe,
            Contagion, IgnoreAllCounter, IgnorePImmune, IgnoreAllImmune, FinalJudgement, FireAura, VengeanceOfAngel, HeavensDoor,
            Preparation, BullsEye, IncEffectHPPotion, IncEffectMPPotion, SoulMP, FullSoulMP, SoulSkillDamageUp, BleedingToxin,
            IgnoreMobDamR, Asura, OmegaBlaster, FlipTheCoin, UnityOfPower, Stimulate, ReturnTeleport, CapDebuff,
            DropRIncrease, IgnoreMobpdpR, BdR, Exceed, DiabolikRecovery, FinalAttackProp, ExceedOverload, DevilishPower,
            OverloadCount, BuckShot, FireBomb, HalfstatByDebuff, SurplusSupply, SetBaseDamage, AmaranthGenerator, StrikerHyperElectric,
            EventPointAbsorb, EventAssemble, StormBringer, ACCR, DEXR, Translucence, PoseType, LightOfSpirit,
            ElementSoul, CosmicOrb, GlimmeringTime, Restoration, ComboCostInc, TrueSight, CrossOverChain, ChillingStep,
            Reincarnation, ReincarnationMission, ReincarnationOnOff, DotBasedBuff, SixthDotBasedBuff, BlessEnsenble, QuiverCatridge, ExtraQuiverCatridge, ExtraQuiverMagic, ExtraQuiverVamp,
            UserControlMob,
            ImmuneBarrier, CriticalGrowing, RelicEmblem, QuickDraw, BowMasterConcentration, ElementFlameBuff, TimeFastABuff, TimeFastBBuff,
            GatherDropR, AimBox2D, PerfectShot, StatusResistance, IncMonsterBattleCaptureRate, DotHealHPPerSecond, DotHealMPPerSecond, SpiritGuard,
            PreReviveOnce, SetBaseDamageByBuff, LimitMP, ReflectDamR, ComboTempest, MHPCutR, MMPCutR, SelfWeakness,
            ElementDarkness, FlareTrick, Ember, Dominion, SiphonVitality, DarknessAscension, BossWaitingLinesBuff, DamageReduce,
            ShadowServant, ShadowIllusion, AddAttackCount, ComplusionSlant, JaguarSummoned, JaguarCount, SSFShootingAttack, DevilCry,
            ShieldAttack, DarkLighting, AttackCountX, BMageDeath, BombTime, NoDebuff, XenonAegisSystem, AngelicBursterSoulSeeker,
            HiddenPossession, NightWalkerBat, NightLordMark, WizardIgnite, DemonFrenzy, ShadowSpear, BattlePvPHelenaMark, BattlePvPHelenaWindSpirit,
            BattlePvPLangEProtection, BattlePvPLeeMalNyunScaleUp, BattlePvPRevive, PinkbeanAttackBuff, RandAreaAttack, BattlePvPMikeShield, BattlePvPMikeBugle, PinkbeanRelax,
            PinkbeanYoYoStack, WindEnergy, NextAttackEnhance, AranCombotempastOption, ViperTimeLeap, RoyalGuardState, RoyalGuardPrepare, MichaelSoulLink,
            MichaelStanceLink, TriflingWhimOnOff, AddRangeOnOff, KinesisPsychicPoint, KinesisPsychicOver, KinesisIncMastery, KinesisPsychicEnergeShield, BladeStance,
            DebuffActiveSkillHPCon, DebuffIncHP, BowMasterMortalBlow, AngelicBursterSoulResonance, Fever, IgnisRore, TeleportMasteryRange, FireBarrier,
            ChangeFoxMan, FixCoolTime, IncMobRateDummy, AdrenalinBoost, AranDrain, AranBoostEndHunt, RWCylinder, RWCombination,
            Unk188_460, RWMagnumBlow, RWBarrier, RWBarrierHeal, RWMaximizeCannon, RWOverHeat, RWMovingEvar, Stigma,
            HeavensDoorCooltime, SealedRuneCooltime, MahasFury, PinPointSalvo, MeltDown, SparkleBurstStage, LightningCascade, BulletParty,
            LoadedDice, Benediction, Unk221_497, VoidStrike, Unk212_504, DivineEcho, WeaponAura, Warrior_AuraWeaponStack,
            ManaOverload, RhoAias, PsychicTornado, SpreadThrow, StackOverTimeSkill, CannonShooter_MiniCannonBall, CannonShooter_RollingCannon, ShadowAssault,
            Unk188_492, Unk188_494, BlitzShield, SplitShot, FreudBlessing, OverloadMode, MastemasMark, WingsOfGlory,
            RiftOfDamnation, SylvidiaFlight, SpotLight, Cyclone, MuscleMemory, TrickBladeMobStat, Overdrive, EtherealForm,
            LastResort, Oblivion, CurseOfCreation, CurseOfDestruction, BlackMageDebuff, HitStackDamR, RadiantOrb, HarmonyLink,
            LefFastCharge, CrystalChargeBuffIcon, SpecterEnergy, SpecterState, BasicCast, ScarletCast, GustCast, AbyssalCast,
            ImpendingDeath, AbyssalRecall, SixthPhoenix, HolyAdvent, BattleFrenzy, ChargeSpellAmplifier, InfinitySpell, ConversionOverdrive,
            LPBattleMode, WillPoison, Unk199_546, HolyMagicShellCooltime, TridentStrike, ComboInstinct, HeroAuraBladeStack, HeroAuraBladeAdv,
            HeroSongOfBlade, KhaliUltimatum, GaleBarrier, GrandCross, QuiverBarrage, InfernoSphere, SwordsOfConsciousness, SwordOfLight,
            PhantomMark, PhantomMarkMobStat, PrimalGrenade, PMdReduce, ForbidOpPotion, ForbidEquipChange, TanadianRuin, AeonianRise,
            BattlePvPLangEClaw, BattlePvPMikeProtection, ProtectiveShield, AncientGuidance, FifthGoddessBless, PinkbeanMatryoshka, AT_Attributes, AT_Energy, AT_Clone,
            AT_HyperClone, AT_ButterflyDream, KeyDownEnable, LW_Ether, LW_Creation, LW_Dike, LW_Wonder, LW_Restore,
            LW_Nobility, LW_Resonance, RuneOfPurification, Unk214_624, DuskDarkness, HastyAura, DrainingAura, BlueAura,
            DarkAura, WeakeningAura, AuraScythe, IceAura, ParashockGuard, DivineForce, IncarnationAura, BlizzardTempest,
            PhotonRay, WrathOfGods, Revenant, RevenantDamage, SilhouetteMirage, AbyssalLightning, LightningSpearMultistrike, RoyalKnights,
            PhantomDefyingFate, SalamanderMischief, LawOfGravity, RepeatingCrossbowCartridge, CrystalGate, ThrowBlasting, SageThreePathsApparition, DarknessAura,
            MuscleMemoryFinale, LiberationOrbPassive, LiberationOrb, EgoWeaponAlpha, RelicUnbound, AfterImageShock, Malice, Possession,
            DeathBlessing, ThanatosDescent, NAAnnihilation, RemainIncense, NAOminousStream, NABrutalPang, NALinkSkill, StrikerChainReaction,
            AdrenalinActivate, YetiFuryGauge, YetiFuryMode, YetiMushroom, YetiMyFriendPepe, YetiCook, DragonVeinReading, MountainSeeds, PeerlessMountain, AbsorptionWater, AbsorptionWind, AbsorptionSun, UnconstrainedDragonVein,
            Unk228_691, Unk228_692, NatureFriend, DragonVeinTrace, StormWhim, SeaSerpent, SerpentStone, SerpentScrew,
            FlameSweep, Cosmos, EnhancePiercing, BlasterFileBunker, EnhanceSniping, UltimateSniping, UltimatePiercing, EnhanceQuadrupleThrow,
            CrusaderPanic, IceAuraZone, HolyWater, EpicDungeonGauge, EpicDungeonMiddleBossBarrier, TriumphFeather, SixthAngelsRay, GrandFinale, WarInTheShade, FlashMirage,
            HolyBlood, OrbitalExplosion, DragonSlave, TranscendentLight, SixthAssassination, SixthCovertShadowBuff, SixthAssassinationDarkSight, ArtificialEvolution, SixthFrozenLightning,
            ElementalKnight, LarknessStop, SummonChakri, SixthResonate, EnhanceNightLordFourSeasons, DarknessShuriken, ShadowAcceleration, ShadowBurst,
            DeceivingBlade, PathFinderForceCompulsion, FlameOrbMultiAttack, SixthStormArrowEx, SixthJavelinStack, SixthCrystalStack, FlashSlashStack, RobotTransition, RobotTransitionRoboLuncherRM7, RobotTransitionMagneticField, RobotTransitionRoboFactory, NaturesBelief, LamourCarte, Joker,
            AdrenalinMaximum, SixthGloryWingJavelinStack, Nightmare, RapidFire, SacredBastion, EnhanceInfernalBreath, DslayerMetamorphosis, RenPlumSwordForm3Stack, RenPlumSwordForm3Shoot,
            Sublimation, RenPlumSwordEx, SpiritAwakening, SpiritAwakeningStack, AuthenticDarkness, IncMaxDamage, HayatoStance, HayatoBooster,
            HayatoPAD, EvasionMaster, Siksin, SelfHyperBodyIncPAD, SelfHyperBodyMaxHP, SelfHyperBodyMaxMP, EvasionUpgrade, CriticalBuffAdd,
            FoxBless, BossDamageRate, SkillDeployment, AntiEvilShield, EyeForEye, ShamanMode, KenjiShadowAttackBuff, SummonProp, Unk216_516,
            SixthHakuman, SixthShinBatto, MarkingStella, RabbitStack, ConstelHornedWhelk, ConstelEagle, ShineMageAppear, ShineMageWitchHat, ShineMageStarlight, ShineMageTrophy,
            ShineMageShine, ShineMageButterfly, ShineMageWolf, ShineMageWolfHowling, ShineMageCapella, ShineMageCircleOfSaviour, ShineMageHourglass
    );



    // -1: no v265 bit known, kept server-side only and never written to a packet
    CharacterTemporaryStat(int bitPos) {
        this.bitPos = bitPos;
        this.val = bitPos < 0 ? 0 : 1 << (31 - bitPos % 32);
        this.pos = bitPos < 0 ? 0 : bitPos / 32;
    }

    public boolean isSent() {
        return bitPos >= 0;
    }

    public static CharacterTemporaryStat getByBitPos(int bitPos) {
        return bitPos < 0 ? null : Arrays.stream(values()).filter(v -> v.getBitPos() == bitPos).findAny().orElse(null);
    }

    /**
     * The client's int-width mask for SetTemporaryStat: if any of these is in the packet's flag, the client reads
     * every regular stat's n as an int, otherwise every n as a short.
     */
    private static final Set<CharacterTemporaryStat> LOCAL_INT_WIDTH = EnumSet.of(
            ShadowPartner, Dance, Cyclone, SpiritLink, CarnivalDefence, DojangLuckyBonus, MagnetArea, VampDeath,
            SoulGazeCriDamR, PowerTransferGauge, IncMaxDamage, OmegaBlaster, ReturnTeleport, SetBaseDamage,
            NaviFlying, QuiverCatridge, ImmuneBarrier, DotHealHPPerSecond, DotHealMPPerSecond, SetBaseDamageByBuff,
            DivineEcho, AranSmashSwing, RWBarrier, BlitzShield, ProtectiveShield, Unk176_493, RideVehicle, MarkingStella,
            RideVehicleExpire
    );

    /**
     * @return whether a SetTemporaryStat packet with these stats in its flag has int-sized n values (one width
     * for the whole packet)
     */
    public static boolean isLocalEncodeInt(Collection<CharacterTemporaryStat> ctsList) {
        for (CharacterTemporaryStat cts : ctsList) {
            if (LOCAL_INT_WIDTH.contains(cts)) {
                return true;
            }
        }
        return false;
    }

    public boolean isIndie() {
        return ordinal() < IndieStatCount.ordinal();
    }

    public boolean isNotRemovedOnDeath() {
        switch (this) {
            case RWCylinder:
            case SurplusSupply:
            case SealedRuneCooltime:
            case HeavensDoorCooltime:
            case HolyMagicShellCooltime:
            case SkillCooltime:
                return true;
        }

        return false;
    }

    public boolean isProtectiveShield() {
        switch (this) {
            case BlitzShield:
            case ProtectiveShield:
            case IndieProtectiveShield:
                return true;
        }

        return false;
    }

    /**
     * The client's own set (mask constant at 0x147f3dea0, tested by 0x1411511c0): these stats add the moving byte.
     */
    public boolean isMovingEffectingStat() {
        switch (this) {
            case Speed:
            case Jump:
            case Stun:
            case Weakness:
            case Slow:
            case Morph:
            case Ghost:
            case BasicStatUp:
            case Attract:
            case DashSpeed:
            case DashJump:
            case Flying:
            case Frozen:
            case Frozen2:
            case Lapidification:
            case IndieSpeed:
            case IndieJump:
            case KeyDownMoving:
            case Mechanic:
            case Magnet:
            case MagnetArea:
            case VampDeath:
            case VampDeathSummon:
            case GiveMeHeal:
            case DarkTornado:
            case NewFlying:
            case NaviFlying:
            case UserControlMob:
            case Dance:
            case SelfWeakness:
            case BattlePvPLeeMalNyunScaleUp:
            case TouchMe:
            case IndieForceSpeed:
            case IndieForceJump:
            case RideVehicle:
            case RideVehicleExpire:
            case IndieGrandCross:
            case Holding:
            case Unk228_691:
            case BattlePvPHelenaWindSpirit:
            case BattlePvPWonkyAttack:
            case BattlePvPWonkyHeal:
            case EnergyCharged:
                return true;
            default:
                return false;
        }
    }

    public int getVal() {
        return val;
    }

    public int getPos() {
        return pos;
    }

    public int getOrder() {
        return ORDER.indexOf(this);
    }

    @Override
    public int compare(CharacterTemporaryStat o1, CharacterTemporaryStat o2) {
        if (o1.getPos() < o2.getPos()) {
            return -1;
        } else if (o1.getPos() > o2.getPos()) {
            return 1;
        }
        // hacky way to circumvent java not having unsigned ints
        int o1Val = o1.getVal();
        if (o1Val == 0x8000_0000) {
            o1Val = Integer.MAX_VALUE;
        }
        int o2Val = o2.getVal();
        if (o2Val == 0x8000_0000) {
            o2Val = Integer.MAX_VALUE;
        }

        if (o1Val > o2Val) {
            // bigger value = earlier in the int => smaller
            return -1;
        } else if (o1Val < o2Val) {
            return 1;
        }
        return 0;
    }

    public int getBitPos() {
        return bitPos;
    }


    public static void main(String[] args) {
//        getBitPosByString("00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 80 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 10 40 21 00 01 00 87 93 03 00 87 00 00 00 00 00 00 00 00 00 00 00"
//        );
        changeCts();
    }

    private static void getBitPosByString(String str) {
        byte[] bArr = Util.getByteArrayByString(str);
        int[] iArr = new int[bArr.length / 4];
        for (int i = 0; i < bArr.length; i += 4) {
            if (i + 3 >= bArr.length) {
                break;
            }
            iArr[i / 4] |= bArr[i];
            iArr[i / 4] |= bArr[i + 1] << 8;
            iArr[i / 4] |= bArr[i + 2] << 16;
            iArr[i / 4] |= bArr[i + 3] << 24;
        }
        for (int i = 0; i < iArr.length; i++) {
            int mask = iArr[i];
            for (CharacterTemporaryStat cts : CharacterTemporaryStat.values()) {
                if (cts.getPos() == i && (cts.getVal() & mask) != 0) {
                    log.warn(String.format("Contains stat %s", cts.toString()));
                }
            }
        }
    }


    private static void changeCts() {
        File file = new File("D:\\Swordie\\Swordie\\src\\main\\java\\net\\swordie\\ms\\client\\character\\skills\\temp\\CharacterTemporaryStat.java");
        int change = 3;
        CharacterTemporaryStat checkOp = null;
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.contains(",") && line.contains("(")) {
                    String[] split = line.split("[()]");
                    String name = split[0];
                    if (!Util.isNumber(split[1])) {
                        log.info(line);
                        continue;
                    }
                    int val = Integer.parseInt(split[1]);
                    CharacterTemporaryStat ih = Arrays.stream(CharacterTemporaryStat.values()).filter(o -> o.toString().equals(name.trim())).findFirst().orElse(null);
                    if (ih != null) {
                        CharacterTemporaryStat start = TreasureHunt;
                        if (ih.ordinal() >= start.ordinal() && ih.ordinal() < WrathOfGods.ordinal()) {
                            if (line.contains("*")) {
                                checkOp = ih;
                            }
                            val += change;
                            log.info(String.format("%s(%d), %s", name, val, start == ih ? "// *" : ""));
                        } else {
                            log.info(line);
                        }
                    } else {
                        log.info(line);
                    }
                } else {
                    log.info(line);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("CharacterTemporaryStat.changeCts", e);
        }
        if (checkOp != null) {
            log.error(String.format("Current op (%s) contains a * (= updated). Be sure to check for overlap.", checkOp));
        }
    }
}
