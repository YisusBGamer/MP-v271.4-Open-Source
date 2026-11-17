package net.swordie.ms.client.character.skills.temp;

import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.connection.OutPacket;

import java.util.List;
import java.util.Map;

import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * The regular part of CSecondaryStat::DecodeForRemote (v265), in the client's read order. A stat can appear more
 * than once when the client reads it in several places.
 */
public class RemoteSecondaryStat {

    private RemoteSecondaryStat() {
    }

    @FunctionalInterface
    interface Encoder {
        void encode(OutPacket outPacket, Option option);
    }

    record Entry(CharacterTemporaryStat cts, Encoder enc) {
    }

    static final Entry[] ENTRIES = {
            new Entry(Speed, (outPacket, o) -> outPacket.encodeByte(o.nOption)),
            new Entry(ComboCounter, (outPacket, o) -> outPacket.encodeByte(o.nOption)),
            new Entry(HammerOfTheRighteous, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(WeaponCharge, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ElementalCharge, (outPacket, o) -> outPacket.encodeShort(o.nOption)),
            new Entry(Stun, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Shock, (outPacket, o) -> outPacket.encodeByte(o.nOption)),
            new Entry(Darkness, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Seal, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Weakness, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(WeaknessMdamage, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Curse, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Slow, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(PvPRaceEffect, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(TimeBomb, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Team, (outPacket, o) -> outPacket.encodeByte(o.nOption)),
            new Entry(DisOrder, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Thread, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Poison, (outPacket, o) -> {
                outPacket.encodeShort(o.xOption);
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ShadowPartner, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DarkSight, (outPacket, o) -> {
            }),
            new Entry(SoulArrow, (outPacket, o) -> {
            }),
            new Entry(Morph, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Ghost, (outPacket, o) -> outPacket.encodeShort(o.nOption)),
            new Entry(Attract, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Magnet, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(MagnetArea, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(NoBulletConsume, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(BanMap, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Barrier, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DojangShield, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ReverseInput, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(RespectPImmune, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(RespectMImmune, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(DefenseAtt, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(DefenseState, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(DojangBerserk, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DojangInvincible, (outPacket, o) -> {
            }),
            new Entry(RepeatEffect, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(StopPortion, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(StopMotion, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Fear, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(MagicShield, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(Flying, (outPacket, o) -> {
            }),
            new Entry(Frozen, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Frozen2, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Web, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DrawBack, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(FinalCut, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BeastFormDamageUp, (outPacket, o) -> {
            }),
            new Entry(Mechanic, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BlessingArmorIncPAD, (outPacket, o) -> {
            }),
            new Entry(Inflation, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Explosion, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DarkTornado, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AmplifyDamage, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HideAttack, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HolyMagicShell, (outPacket, o) -> {
            }),
            new Entry(DevilishPower, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SpiritLink, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Event, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Event2, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DeathMark, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(PainMark, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Lapidification, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(VampDeath, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(VampDeathSummon, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(VenomSnake, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(PyramidEffect, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(PinkbeanRollingGrade, (outPacket, o) -> outPacket.encodeByte(o.nOption)),
            new Entry(IgnoreTargetDEF, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Invisible, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Judgement, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(KeyDownAreaMoving, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(StackBuff, (outPacket, o) -> outPacket.encodeShort(o.nOption)),
            new Entry(Larkness, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ReshuffleSwitch, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SpecialAction, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(StopForceAtomInfo, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SoulGazeCriDamR, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(PowerTransferGauge, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BlitzShield, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AffinitySlug, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SoulExalt, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HiddenPieceOn, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SmashStack, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(MobZoneState, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(GiveMeHeal, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(TouchMe, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Contagion, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
                outPacket.encodeInt(o.xOption);
            }),
            new Entry(IgnoreAllCounter, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(IgnorePImmune, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(IgnoreAllImmune, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(FinalJudgement, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(FireAura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(VengeanceOfAngel, (outPacket, o) -> {
            }),
            new Entry(HeavensDoor, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DamAbsorbShield, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AntiMagicShell, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(NotDamaged, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BleedingToxin, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(WindBreakerFinal, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(KarmaBlade, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(IgnoreMobDamR, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Asura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(OmegaBlaster, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
                outPacket.encodeInt(o.tOption);
            }),
            new Entry(UnityOfPower, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Stimulate, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ReturnTeleport, (outPacket, o) -> {
                outPacket.encodeByte(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(CapDebuff, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(OverloadCount, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(FireBomb, (outPacket, o) -> {
                outPacket.encodeByte(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SurplusSupply, (outPacket, o) -> outPacket.encodeByte(o.nOption)),
            new Entry(NewFlying, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(NaviFlying, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AmaranthGenerator, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(CygnusElementSkill, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(StrikerHyperElectric, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(EventPointAbsorb, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(EventAssemble, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Translucence, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(PoseType, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(LightOfSpirit, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ElementSoul, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(GlimmeringTime, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Reincarnation, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Beholder, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(QuiverCatridge, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(UserControlMob, (outPacket, o) -> {
            }),
            new Entry(ImmuneBarrier, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.tOption);
            }),
            new Entry(FullSoulMP, (outPacket, o) -> {
                outPacket.encodeInt(o.rOption);
                outPacket.encodeInt(o.xOption);
            }),
            new Entry(AntiMagicShell, (outPacket, o) -> {
                outPacket.encodeByte(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Dance, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SpiritGuard, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
                outPacket.encodeInt(o.tOption);
            }),
            new Entry(MastemasMark, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ComboTempest, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HalfstatByDebuff, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ComplusionSlant, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(JaguarSummoned, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BombTime, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(MeltDown, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SparkleBurstStage, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(LightningCascade, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BulletParty, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(LoadedDice, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Benediction, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DarkLighting, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AttackCountX, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(FireBarrier, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(KeyDownMoving, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(MichaelSoulLink, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(KinesisPsychicEnergeShield, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BladeStance, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
                outPacket.encodeInt(o.xOption);
            }),
            new Entry(Fever, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AdrenalinBoost, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(RWBarrier, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(Unk188_460, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(RWMagnumBlow, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(SerpentScrew, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(Cosmos, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(GuidedArrow, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(LefBuffMastery, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(TempSecondaryStat, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Stigma, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DivineEcho, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ConstelEagle, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(ConstelHornedWhelk, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ShineMageAppear, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(RhoAias, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(PsychicTornado, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(ManaOverload, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(PerfectShot, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SylvidiaFlight, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SpotLight, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(OverloadMode, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(FreudBlessing, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BigHammerOfTheRighteous, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Overdrive, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(EtherealForm, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(LastResort, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Oblivion, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(CurseOfCreation, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(CurseOfDestruction, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BlackMageDebuff, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HitStackDamR, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(WingsOfGlory, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HarmonyLink, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(LefFastCharge, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SpecterState, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ImpendingDeath, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AbyssalRecall, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SixthPhoenix, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(WillPoison, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SwordOfLight, (outPacket, o) -> {
            }),
            new Entry(GrandCross, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ProtectiveShield, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(PinkbeanMatryoshka, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AT_HyperClone, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(TanadianRuin, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AeonianRise, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(QuiverBarrage, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(LW_Nobility, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(RuneOfPurification, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Unk214_624, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DuskDarkness, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HastyAura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DrainingAura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BlueAura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DarkAura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(WeakeningAura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AuraScythe, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(IceAura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ParashockGuard, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DivineForce, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(IncarnationAura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BlizzardTempest, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(PhotonRay, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DarknessAura, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SilhouetteMirage, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(LiberationOrb, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ThanatosDescent, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(NAAnnihilation, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(YetiFuryMode, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AbsorptionWater, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AbsorptionWind, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AbsorptionSun, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(IceAuraZone, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(FlashMirage, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HolyBlood, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Infinity, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(TeleportMasteryOn, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ChillingStep, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BlessingArmor, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(TranscendentLight, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ArtificialEvolution, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(DemonFrenzy, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(CrystalGate, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(KinesisPsychicPoint, (outPacket, o) -> {
                outPacket.encodeInt(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(GrandFinale, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(NaturesBelief, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AdrenalinMaximum, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HiddenPossession, (outPacket, o) -> outPacket.encodeInt(o.nOption)),
            new Entry(DslayerMetamorphosis, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Exceed, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(RobotTransitionRoboLuncherRM7, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(RobotTransitionMagneticField, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(RobotTransitionRoboFactory, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Sublimation, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(RenPlumSwordEx, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ShamanMode, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HayatoStance, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
                outPacket.encodeInt(o.xOption);
            }),
            new Entry(HayatoBooster, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(HayatoPAD, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SelfHyperBodyIncPAD, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SelfHyperBodyMaxHP, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SelfHyperBodyMaxMP, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(CriticalBuffAdd, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(BossDamageRate, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Stance, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SkillDeployment, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(AntiEvilShield, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(EyeForEye, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SplitShot, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(Unk216_516, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SixthHakuman, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(SixthShinBatto, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(ConstelEagle, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
            new Entry(MukHyun_HO_SIN_GANG_GI, (outPacket, o) -> {
                outPacket.encodeShort(o.nOption);
                outPacket.encodeInt(o.rOption);
            }),
    };

    public static void encode(OutPacket outPacket, Map<CharacterTemporaryStat, List<Option>> stats) {
        for (Entry e : ENTRIES) {
            List<Option> options = stats.get(e.cts);
            if (options == null || options.isEmpty()) {
                continue;
            }
            e.enc.encode(outPacket, options.get(0));
        }
    }
}
