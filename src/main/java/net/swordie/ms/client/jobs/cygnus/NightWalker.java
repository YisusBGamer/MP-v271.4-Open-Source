package net.swordie.ms.client.jobs.cygnus;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.SkillUseSource;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtom;
import net.swordie.ms.client.character.skills.atom.forceatom.ForceAtomCollisionInfo;
import net.swordie.ms.client.character.skills.info.*;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.*;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.ForceAtomEnum;
import net.swordie.ms.enums.LeaveType;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.AffectedArea;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import net.swordie.ms.constants.SkillChangeConstants;
import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class NightWalker extends Noblesse {

    public static final int ELEMENTAL_HARMONY_LUK = 10000249;

    public static final int LUCKY_SEVEN = 14001020;
    public static final int DARK_ELEMENTAL = 14001021; //Buff (Mark of Darkness)
    public static final int HASTE = 14001022; //Buff
    public static final int DARK_SIGHT = 14001023; //Buff
    public static final int DARK_SIGHT_V271 = 14001031;

    public static final int THROWING_BOOSTER = 14101022; //Buff


    public static final int DARK_SERVANT = 14111024; //Buff
    public static final int POISON_BOMB = 14111006;
    public static final int SPIRIT_PROJECTION = 14111025; //Buff
    public static final int SHADOW_MOMENTUM = 14110032; // inertia: the client asks for a stack after moving
    public static final int DARKNESS_ASCENDING = 14111030; // on/off buff, used up by a fatal hit
    public static final int SHADOW_SPARK = 14101028;
    public static final int SHADOW_SPARK_FINISHER = 14101029;

    public static final int DARK_OMEN = 14121003; //Summon
    public static final int SHADOW_STITCH = 14121004; //Special Attack (Bind Debuff)
    public static final int CALL_OF_CYGNUS_NW = 14121000; //Buff
    public static final int VITALITY_SIPHON = 14120009;
    public static final int VITALITY_SIPHON_SHIELD = 14120011;
    public static final int VITALITY_SIPHON_EXTRA_POINT = 14120049;
    public static final int VITALITY_SIPHON_REINFORCE = 14120050;
    public static final int VITALITY_SIPHON_PREPARATION = 14120051;

    public static final int GLORY_OF_THE_GUARDIANS_NW = 14121053;
    public static final int SHADOW_ILLUSION = 14121054;
    public static final int DOMINION = 14121052;
    public static final int DARK_EVASION = 14121013;
    public static final int SILENCE = 14141500;
    public static final int SILENCE_BAT = 14141502;
    public static final int SILENCE_STARS = 14141501; // the Shadow Throwing Stars (SecondAtom)
    public static final int ABYSSAL_DARKNESS = 14141019; // Abyssal Strike: a bonus attack

    // V Skill
    public static final int SHADOW_SPEAR = 400041008;
    public static final int SHADOW_SPEAR_AA_SMALL = 400040008;
    public static final int SHADOW_SPEAR_AA_LARGE = 400041019;
    // v271: a shadow that mimics the user (ShadowPartner, reason = skill id); the client draws it
    public static final int GREATER_DARK_SERVANT = 400041028;
    public static final int HEXA_GREATER_DARK_SERVANT = 500061080;
    // v271: the immovable, swappable shadow summon that was Greater Dark Servant
    public static final int SHADOW_SLIDE = 400041095;
    public static final int HEXA_SHADOW_SLIDE = 500061004;
    public static final int HEXA_SHADOW_BITE = 500061035;
    public static final int SHADOW_BITE = 400041037;
    public static final int RAPID_THROW = 400041059;


    //Bats
    public static final int SHADOW_BAT_SKILL = 14000027;
    public static final int SHADOW_BAT = 14001027; //Buff (Shadow Bats) (ON/OFF)
    public static final int SHADOW_BAT_ATOM = 14000028;
    public static final int SHADOW_BAT_FROM_MOB_ATOM = 14000029;
    public static final int HEXA_SHADOW_BAT = 14141004; // Buff (ON/OFF), replaces Shadow Bat on the quickslot
    public static final int HEXA_SHADOW_BAT_SKILL = 14141005;
    public static final int HEXA_SHADOW_BAT_ATOM = 14141006;
    public static final int HEXA_SHADOW_BAT_FROM_MOB_ATOM = 14141007;
    public static final int RAVENOUS_BAT = 14121016; // Buff (ON/OFF), not together with Shadow Bat
    public static final int RAVENOUS_BAT_SKILL = 14120017;
    public static final int RAVENOUS_BAT_ATOM = 14120018;
    public static final int HEXA_RAVENOUS_BAT = 14141012; // Buff (ON/OFF)
    public static final int HEXA_RAVENOUS_BAT_SKILL = 14141013;
    public static final int HEXA_RAVENOUS_BAT_ATOM = 14141014;
    public static final int DARK_OMEN_WEAKENED = 14121012;
    // Bat Affinity II / III: every z bats an enhanced one, flying at the target of the next throwing-star hit
    public static final int ENHANCED_BAT = 14110033;
    public static final int ENHANCED_BAT_ATOM = 14110034; // bounces u2 times; its explosions are the client's
    public static final int HEXA_ENHANCED_BAT = 14141008;
    public static final int HEXA_ENHANCED_BAT_ATOM = 14141009;
    public static final int ENHANCED_RAVENOUS_BAT = 14120019;
    public static final int ENHANCED_RAVENOUS_BAT_ATOM = 14120020;
    public static final int HEXA_ENHANCED_RAVENOUS_BAT = 14141015;
    public static final int HEXA_ENHANCED_RAVENOUS_BAT_ATOM = 14141016;
    public static final int DARK_SIGHT_2 = 14001003;
    public static final int SHADOW_PARTNER = 14111000;

    private static boolean isEnhancedBat(int skillId) {
        return skillId == ENHANCED_BAT || skillId == HEXA_ENHANCED_BAT || skillId == ENHANCED_RAVENOUS_BAT
                || skillId == HEXA_ENHANCED_RAVENOUS_BAT;
    }
    public static final int HEXA_DARK_OMEN_WEAKENED = 14141020;

    public static boolean isShadowBatSummon(int skillId) {
        return skillId == SHADOW_BAT_SKILL || skillId == HEXA_SHADOW_BAT_SKILL
                || skillId == RAVENOUS_BAT_SKILL || skillId == HEXA_RAVENOUS_BAT_SKILL;
    }

    public static boolean isDarkOmenSummon(int skillId) {
        return isDarkOmen(skillId) || skillId == DARK_OMEN_V271 || skillId == HEXA_DARK_OMEN_V271;
    }

    public static boolean isShadowBatAtom(int skillId) {
        return skillId == SHADOW_BAT_ATOM || skillId == SHADOW_BAT_FROM_MOB_ATOM
                || skillId == HEXA_SHADOW_BAT_ATOM || skillId == HEXA_SHADOW_BAT_FROM_MOB_ATOM
                || skillId == RAVENOUS_BAT_ATOM || skillId == HEXA_RAVENOUS_BAT_ATOM;
    }

    public static boolean isShadowBatFromMobAtom(int skillId) {
        return skillId == SHADOW_BAT_FROM_MOB_ATOM || skillId == HEXA_SHADOW_BAT_FROM_MOB_ATOM;
    }

    private static boolean isBatToggle(int skillId) {
        return skillId == SHADOW_BAT || skillId == HEXA_SHADOW_BAT || skillId == RAVENOUS_BAT || skillId == HEXA_RAVENOUS_BAT;
    }

    // the bat toggle that is on, 0 if none
    private int batToggle() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        return tsm.hasStat(NightWalkerBat) ? tsm.getOption(NightWalkerBat).rOption : 0;
    }

    private boolean ravenousBat() {
        int t = batToggle();
        return t == RAVENOUS_BAT || t == HEXA_RAVENOUS_BAT;
    }

    private boolean hexaBat() {
        int t = batToggle();
        return t == HEXA_SHADOW_BAT || t == HEXA_RAVENOUS_BAT
                || (t == SHADOW_BAT && chr.hasSkill(HEXA_SHADOW_BAT))
                || (t == RAVENOUS_BAT && chr.hasSkill(HEXA_RAVENOUS_BAT));
    }

    // the skill whose data (z, x, y, prop) and level the current bats use
    private int batDataSkill() {
        if (ravenousBat()) {
            return hexaBat() ? HEXA_RAVENOUS_BAT : RAVENOUS_BAT;
        }
        return hexaBat() ? HEXA_SHADOW_BAT : SHADOW_BAT;
    }

    private int batSummonSkill() {
        if (ravenousBat()) {
            return hexaBat() ? HEXA_RAVENOUS_BAT_SKILL : RAVENOUS_BAT_SKILL;
        }
        return hexaBat() ? HEXA_SHADOW_BAT_SKILL : SHADOW_BAT_SKILL;
    }

    private int batAtomSkill() {
        if (ravenousBat()) {
            return hexaBat() ? HEXA_RAVENOUS_BAT_ATOM : RAVENOUS_BAT_ATOM;
        }
        return hexaBat() ? HEXA_SHADOW_BAT_ATOM : SHADOW_BAT_ATOM;
    }
    public static final int BAT_AFFINITY = 14100027; //Summon Upgrade
    public static final int BAT_AFFINITY_II = 14110029; //Summon Upgrade
    public static final int BAT_AFFINITY_III = 14120008; //Summon Upgrade

    //Dark Elemental
    public static final int ADAPTIVE_DARKNESS = 14100026;
    public static final int ADAPTIVE_DARKNESS_II = 14110028;
    public static final int ADAPTIVE_DARKNESS_III = 14120007;

    //Attacks
    public static final int QUINTUPLE_STAR = 14121001;
    public static final int QUINTUPLE_STAR_FINISHER = 14121002;

    public static final int QUAD_STAR = 14111020;
    public static final int QUAD_STAR_FINISHER = 14111021;

    public static final int TRIPLE_THROW = 14101020;
    public static final int TRIPLE_THROW_FINISHER = 14101021;
    public static final int HEXA_QUINTUPLE_STAR = 14141000;
    public static final int HEXA_QUINTUPLE_STAR_JET_BLACK = 14141002;
    public static final int HEXA_DOMINION = 14141018; // HEXA Dominion
    public static final int HEXA_DARK_OMEN = 14141017; // HEXA Dark Omen
    public static final int DARK_OMEN_V271 = 14121021; // invisible 7 s summon, same family as 14121003/14121012
    public static final int HEXA_DARK_OMEN_V271 = 14141021;

    private static final int DOMINION_STANCE = 100; // hardcode-ok

    private static final int[] addedSkills = new int[]{
            ELEMENTAL_HARMONY_LUK
    };

    private static final int[] darkEleSkills = new int[]{
            DARK_ELEMENTAL,
            ADAPTIVE_DARKNESS,
            ADAPTIVE_DARKNESS_II,
            ADAPTIVE_DARKNESS_III,
    };

    private static final int[] batSkills = new int[]{
            SHADOW_BAT,
            BAT_AFFINITY,
            BAT_AFFINITY_II,
            BAT_AFFINITY_III,
    };

    private int throwingStarCount = 0;
    private int darkOmenCount = 0;
    private Summon darkServant;
    private int darkSightCDCount = 0;
    private boolean ascendingRevive = false;
    private boolean servantBeforeIllusion = false;

    public NightWalker(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id) || chr.getSkill(id).getCurrentLevel() == 0) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            removeSkillBeforeFourthJob(CALL_OF_CYGNUS_NW);
        }
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isNightWalker(id);
    }


    private void applyDarkServant() {
        Skill skill = chr.getSkill(DARK_SERVANT);
        int slv = skill.getCurrentLevel();
        darkServant = Summon.getSummonBy(chr, skill.getSkillId(), slv);
        Field field = chr.getField();
        darkServant.setAvatarLook(chr.getAvatarData().getAvatarLook());
        darkServant.setMoveAbility(MoveAbility.WalkClone);
        darkServant.setActionDelay(400);
        darkServant.setMovementDelay(30);
        darkServant.setAssistType(AssistType.None);
        darkServant.setFlip(true);
        field.spawnSummonAndRemoveOld(darkServant);
    }

    private Summon getGreaterDarkServant() {
        Summon s = chr.getField().getSummonByChrAndSkillId(chr, HEXA_SHADOW_SLIDE);
        return s != null ? s : chr.getField().getSummonByChrAndSkillId(chr, SHADOW_SLIDE);
    }

    public void swapWithServant() {
        Summon summon = getGreaterDarkServant();
        if (summon != null && summon.getCount() > 0) {
            summon.setCount(summon.getCount() - 1);
            chr.getField().broadcastPacket(Summoned.summonUpgradeStage(summon, 2, 0, 0));
            chr.getField().broadcastPacket(Summoned.effect(summon, 2, 0));
            Position servantPos = summon.getPosition().deepCopy();
            chr.write(UserLocal.greaterDarkServantSwapResult(servantPos, chr.getPosition().deepCopy(), false, false));
            summon.setPosition(chr.getPosition());
            chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillSpecial(summon.getSkillID())));
        }
    }

    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        if (cts == ShadowIllusion) {
            // the Dark Servant that Shadow Illusion replaced comes back
            if (servantBeforeIllusion && chr.getField() != null && chr.hasSkill(DARK_SERVANT)) {
                Skill ds = chr.getSkill(DARK_SERVANT);
                SkillInfo dsi = SkillData.getSkillInfoById(DARK_SERVANT);
                applyDarkServant();
                if (!chr.getTemporaryStatManager().hasStat(ShadowServant)) {
                    chr.getTemporaryStatManager().putCharacterStatValue(ShadowServant,
                            new Option(1, DARK_SERVANT, dsi.getValue(time, ds.getCurrentLevel())));
                }
                chr.getTemporaryStatManager().sendSetStatPacket();
            }
            servantBeforeIllusion = false;
        } else if (cts == DarkSight) {
            Skill skill = chr.getSkill(DARK_SIGHT);
            int cd = skill == null ? 0
                    : SkillData.getSkillInfoById(skill.getSkillId()).getValue(cooltime, skill.getCurrentLevel())
                    * darkSightCDCount * 1000;
            if (cd > 0) {
                chr.addSkillCoolTime(skill.getSkillId(), cd);
            }
        }

        super.handleRemoveCTS(cts, o);
    }

    /** The right-click toggle of Dominion turned off: its mode and buffs end (the cooldown from the cast stays). */
    public void toggleOff(int toggleKey) {
        if (toggleKey != DOMINION) {
            return;
        }
        var tsm = chr.getTemporaryStatManager();
        boolean ended = false;
        for (int id : new int[]{DOMINION, HEXA_DOMINION}) {
            ended |= !tsm.removeStatsBySkill(id).isEmpty();
        }
        if (ended) {
            tsm.sendResetStatPacket();
        }
    }

    public int alterCooldownSkill(int skillId, int slv, AttackInfo attackInfo, SkillUseInfo sui, SkillUseSource source) {
        switch (skillId) {
            case DARK_SIGHT:
                return 0;
            case DARKNESS_ASCENDING:
                // the cooldown starts when the buff is used up, not on the cast
                return ascendingRevive ? -1 : 0;
        }

        return super.alterCooldownSkill(skillId, slv, attackInfo, sui, source);
    }


    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        breakDarkSightOnAttack(attackInfo, DARK_SIGHT, DARK_SIGHT_V271);
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = (byte) skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }
        Field field = chr.getField();
        if (hasHitMobs) {
            // Handling Shadow Bats
            if (tsm.hasStat(NightWalkerBat) && isThrowingStarSkill(attackInfo.skillId)) {
                dominionBats();
                shadowBats(attackInfo);
            }
            if (isDarkOmen(attackInfo.skillId) && chr.hasSkill(DARK_OMEN)) {
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) field.getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    if (darkOmenCount < chr.getSkillStatValue(x, DARK_OMEN)) {
                        darkOmenCount++;
                        continue;
                    }
                    if(tsm.hasStat(NightWalkerBat)) {
                        shadowBats(attackInfo);
                    }
                    darkOmenCount = 0;
                }
            }

            // Handling Dark Elemental
            applyDarkElementalOnMob(attackInfo);

            // Dark Omen's y is bats per x hits, not a cooltime reduction (none in v265/v271 data)
            si = SkillData.getSkillInfoById(attackInfo.skillId);

            if (tsm.hasStatBySkillId(SHADOW_SPEAR) && smallSpearsByServer() &&
                    si != null &&
                    si.getElemAttr().equalsIgnoreCase("d") &&
                    !isDarkOmen(attackInfo.skillId) &&
                    attackInfo.skillId != SHADOW_SPEAR_AA_SMALL &&
                    attackInfo.skillId != SHADOW_SPEAR_AA_LARGE) {
                placeShadowSpearAA(attackInfo);
            }
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        switch (attackInfo.skillId) {
            case SHADOW_STITCH:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = Math.min(si.getValue(time, slv) + (si.getValue(y, slv) > 0 ? attackInfo.mobCount / si.getValue(y, slv) : 0), si.getValue(s, slv));
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.Freeze, o1);
                }
                break;
            case DOMINION:
            case HEXA_DOMINION:
                // the mode itself (the client reads it); invincible only while casting (y ms)
                tsm.putCharacterStatValue(Dominion, new Option(1, skillID, si.getValue(time, slv)));
                dominionBatsGiven = false;
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = Math.max(1, si.getValue(y, slv));
                o1.setInMillis(true);
                tsm.putCharacterStatValue(NotDamaged, o1);
                o2.nOption = si.getValue(indieCr, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(CriticalBuff, o2);
                o3.nOption = DOMINION_STANCE;
                o3.rOption = skillID;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Stance, o3);
                o4.rOption = skillID;
                o4.nOption = si.getValue(indiePMdR, slv);
                o4.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePMdR, o4);
                tsm.sendSetStatPacket();
                break;
            case SHADOW_BITE:
            case HEXA_SHADOW_BITE:
                createShadowBiteForceAtom(attackInfo);
                break;
            case SHADOW_BAT_ATOM:
            case HEXA_SHADOW_BAT_ATOM:
            case RAVENOUS_BAT_ATOM:
            case HEXA_RAVENOUS_BAT_ATOM:
                healByShadowBat();
                break;
            case SHADOW_SPEAR_AA_SMALL:
                AffectedArea aa = (AffectedArea) chr.getField().getLifeByObjectID(attackInfo.affectedAreaObjId);
                if (aa != null) {
                    chr.getField().removeLife(aa);
                }
                break;
        }

        if (hasHitMobs) {
            silenceStars(attackInfo);
            abyssalDarkness(attackInfo);
        }
        super.handleAttack(c, attackInfo);
    }

    private final java.util.Map<Integer, Integer> abyssalHits = new java.util.HashMap<>();

    // Abyssal Darkness: every x throwing-star hits on an enemy with Mark of Darkness, Abyssal Strike (stronger per
    // stack, up to y)
    private void abyssalDarkness(AttackInfo attackInfo) {
        int slv = chr.getSkillLevel(ABYSSAL_DARKNESS);
        if (slv <= 0 || !isThrowingStarSkill(attackInfo.skillId)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(ABYSSAL_DARKNESS);
        int need = Math.max(1, si.getValue(x, slv));
        if (abyssalHits.size() > 200) {
            abyssalHits.clear();
        }
        java.util.List<Integer> targets = new java.util.ArrayList<>();
        int stacks = 0;
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
            if (mob == null || mai.mobDies || !mob.getTemporaryStat().hasCurrentMobStat(MobStat.ElementDarkness)) {
                continue;
            }
            if (abyssalHits.merge(mai.mobId, 1, Integer::sum) >= need) {
                abyssalHits.remove(mai.mobId);
                targets.add(mai.mobId);
                stacks = Math.max(stacks, mob.getTemporaryStat().getCurrentOptionsByMobStat(MobStat.ElementDarkness).nOption);
            }
        }
        if (!targets.isEmpty()) {
            stacks = Math.min(stacks, Math.max(1, si.getValue(y, slv)));
            chr.write(UserLocal.userBonusAttackRequest(ABYSSAL_DARKNESS, targets, 0, stacks, null, true));
        }
    }

    private long silenceUntil, silenceNext;

    // Silence: for time sec after it lands, hits turn the Shadow Bat into bulletCount Shadow Throwing Stars that
    // chase the enemy, at most once a cooldown (the stars' skill)
    private void silenceStars(AttackInfo attackInfo) {
        long now = Util.getCurrentTimeLong();
        int slv = chr.getSkillLevel(SILENCE);
        if (slv <= 0) {
            return;
        }
        if (attackInfo.skillId == SILENCE) {
            silenceUntil = now + SkillData.getSkillInfoById(SILENCE).getValue(time, slv) * 1000L;
            silenceNext = 0;
            return;
        }
        SkillInfo starSi = SkillData.getSkillInfoById(SILENCE_STARS);
        if (now >= silenceUntil || now < silenceNext || attackInfo.skillId == SILENCE_STARS || starSi == null
                || starSi.getSecondAtomInfos().isEmpty() || isShadowBatAtom(attackInfo.skillId)
                || SkillConstants.isForceAtomSkill(attackInfo.skillId)) {
            return;
        }
        Mob target = attackInfo.mobAttackInfo.stream().map(mai -> (Mob) chr.getField().getLifeByObjectID(mai.mobId))
                .filter(Objects::nonNull).filter(m -> m.getHp() > 0).findFirst().orElse(null);
        if (target == null) {
            return;
        }
        silenceNext = now + Math.max(1, starSi.getValue(cooltime, slv)) * 1000L;
        int count = Math.max(1, starSi.getValue(bulletCount, slv));
        java.util.List<net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom> atoms = new java.util.ArrayList<>();
        for (int i = 0; i < count; i++) {
            var sai = starSi.getSecondAtomInfos().get(Math.min(i, starSi.getSecondAtomInfos().size() - 1));
            atoms.add(new net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom(chr.getNewSecondAtomKey(),
                    sai.getDataIndex(), i, chr.getId(), target.getObjectId(), SILENCE_STARS, sai.getCreateDelay(),
                    sai.getEnableDelay(), sai.getRotate(), sai.getExpire(), 360, sai.getAttackableCount(),
                    chr.getPosition().deepCopy(), sai.getCustomList()));
        }
        chr.spawnSecondAtoms(atoms);
    }

    private static boolean isDarkOmen(int skillId) {
        return skillId == DARK_OMEN || skillId == DARK_OMEN_WEAKENED
                || skillId == HEXA_DARK_OMEN || skillId == HEXA_DARK_OMEN_WEAKENED;
    }

    private boolean isThrowingStarSkill(int skillId) {
        return skillId == LUCKY_SEVEN
                || skillId == TRIPLE_THROW
                || skillId == QUAD_STAR
                || skillId == SHADOW_SPARK
                || skillId == QUINTUPLE_STAR
                || skillId == HEXA_QUINTUPLE_STAR
                || skillId == HEXA_QUINTUPLE_STAR_JET_BLACK;
    }

    // handling Shadow Bats / Ravenous Bats
    private void shadowBats(AttackInfo attackInfo) {
        int batSkill = batDataSkill();
        if (!chr.hasSkill(batSkill)) {
            return;
        }
        Skill skill = chr.getSkill(batSkill);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        int shadowBatSize = getCurrentShadowBats().size();
        // Create ShadowBat forceAtom
        if (shadowBatSize > 0 && Util.succeedProp(getShadowBatAttackProp())) {
            doShadowBatAttack(attackInfo, getCurrentShadowBats().get(0));
        }
        fireEnhancedBat(attackInfo);
        if (shadowBatSize < getMaxShadowBats()) {
            // Create ShadowBat every 3 hits
            if (throwingStarCount < si.getValue(z, slv)) {
                throwingStarCount++;
                return;
            }
            throwingStarCount = 0;
            createShadowBat();
        }
    }

    private void makeShadowBatInvisible(Summon shadowBat) {
        chr.getField().broadcastPacket(Summoned.summonedSkill(shadowBat, (byte) 0, 0, null));
    }

    private void doShadowBatAttack(AttackInfo attackInfo, Summon shadowBat) {
        makeShadowBatInvisible(shadowBat);
        removeShadowBat(shadowBat);
        createBatForceAtom(attackInfo, shadowBat);
    }

    private void removeShadowBat(Summon shadowBat) {
        chr.getField().removeSummon(shadowBat);
    }

    public List<Summon> getCurrentShadowBats() {
        return chr.getField().getSummons().stream().filter(s -> isShadowBatSummon(s.getSkillID()) && s.getChr() == chr).collect(Collectors.toList());
    }

    private void createShadowBat() {
        Summon shadowBat = Summon.getSummonByNoCTS(chr, batSummonSkill(), chr.getSkillLevel(batDataSkill()));
        shadowBat.setMoveAbility(MoveAbility.Fly);
        shadowBat.setAssistType(AssistType.None);
        shadowBat.setMoveAction((byte) 5);
        // w sec (60); setSummonTerm takes seconds
        int batTime = chr.getSkillStatValue(w, batDataSkill());
        shadowBat.setSummonTerm(batTime > 0 ? batTime : 60);
        // the client draws the bat from the template skill's summon node: only Bat Affinity III has one
        // (Shadow Bat / Affinity I / II have none and the client throws on the first action it can't load)
        if (ravenousBat()) {
            shadowBat.setTemplateId(batDataSkill());
        } else {
            shadowBat.setTemplateId(chr.hasSkill(BAT_AFFINITY_III) ? BAT_AFFINITY_III : 0);
        }
        chr.getField().spawnSummon(shadowBat);
        countForEnhancedBat();
    }

    private int batsSinceEnhanced;

    private int enhancedBatSource() {
        return ravenousBat() ? BAT_AFFINITY_III : BAT_AFFINITY_II;
    }

    private Summon getEnhancedBat() {
        return chr.getField().getSummons().stream().filter(s -> s.getChr() == chr && isEnhancedBat(s.getSkillID()))
                .findFirst().orElse(null);
    }

    private void countForEnhancedBat() {
        int source = enhancedBatSource();
        if (!chr.hasSkill(source) || ++batsSinceEnhanced < Math.max(1, chr.getSkillStatValue(z, source))) {
            return;
        }
        batsSinceEnhanced = 0;
        if (getEnhancedBat() != null) {
            return;
        }
        boolean hexa = hexaBat();
        int batId = ravenousBat() ? (hexa ? HEXA_ENHANCED_RAVENOUS_BAT : ENHANCED_RAVENOUS_BAT)
                : (hexa ? HEXA_ENHANCED_BAT : ENHANCED_BAT);
        int slv = hexa ? chr.getSkillLevel(batDataSkill()) : chr.getSkillLevel(source);
        Summon bat = Summon.getSummonByNoCTS(chr, batId, Math.max(1, slv));
        bat.setMoveAbility(MoveAbility.Fly);
        bat.setAssistType(AssistType.None);
        bat.setMoveAction((byte) 5);
        int batTime = chr.getSkillStatValue(w, batDataSkill());
        bat.setSummonTerm(batTime > 0 ? batTime : 60);
        chr.getField().spawnSummon(bat);
    }

    private void fireEnhancedBat(AttackInfo attackInfo) {
        Summon bat = getEnhancedBat();
        if (bat == null) {
            return;
        }
        Field field = chr.getField();
        Mob mob = (Mob) attackInfo.mobAttackInfo.stream().map(mai -> field.getLifeByObjectID(mai.mobId))
                .filter(Objects::nonNull).findFirst().orElse(null);
        if (mob == null) {
            return;
        }
        makeShadowBatInvisible(bat);
        removeShadowBat(bat);
        int id = bat.getSkillID();
        int atom;
        ForceAtomEnum fae;
        switch (id) {
            case HEXA_ENHANCED_BAT -> {
                atom = HEXA_ENHANCED_BAT_ATOM;
                fae = ForceAtomEnum.NIGHT_WALKER_HEXA_BAT_ENHANCED;
            }
            case ENHANCED_RAVENOUS_BAT -> {
                atom = ENHANCED_RAVENOUS_BAT_ATOM;
                fae = ForceAtomEnum.NIGHT_WALKER_RAVENOUS_ENHANCED;
            }
            case HEXA_ENHANCED_RAVENOUS_BAT -> {
                atom = HEXA_ENHANCED_RAVENOUS_BAT_ATOM;
                fae = ForceAtomEnum.NIGHT_WALKER_HEXA_RAVENOUS_ENHANCED;
            }
            default -> {
                atom = ENHANCED_BAT_ATOM;
                fae = ForceAtomEnum.NIGHT_WALKER_BAT_ENHANCED;
            }
        }
        ForceAtomInfo fai = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), 1, 5,
                (int) Util.getAngleOfTwoPositions(chr.getPosition(), mob.getPosition()), 500, Util.getCurrentTime(), 0, 0,
                new Position(bat.getX() - chr.getPosition().getX(), bat.getY() - chr.getPosition().getY()));
        ForceAtom fa = new ForceAtom(false, 0, chr.getId(), fae,
                true, mob.getObjectId(), atom, fai, chr.getRectAround(new Rect(-20, -20, 20, 20)), 0, 0,
                new Position(), 0, new Position(), 0);
        if (id == ENHANCED_BAT || id == HEXA_ENHANCED_BAT) {
            // the Ravenous ones bite their target three times and don't bounce
            fa.setMaxRecreationCount(chr.getSkillStatValue(u2, BAT_AFFINITY_II) + 1);
        }
        chr.createForceAtom(fa);
    }

    private void createBatForceAtom(AttackInfo attackInfo, Summon shadowBat) {
        Field field = chr.getField();
        Rect rect = chr.getRectAround(new Rect(-300, -300, 300, 300));
        Mob mob = (Mob) attackInfo.mobAttackInfo.stream().map(mai -> field.getLifeByObjectID(mai.mobId)).filter(Objects::nonNull).findFirst().orElse(null);
        if (mob == null) {
            if (field.getMobsInRect(rect).size() <= 0) {
                return;
            }
            mob = Util.getRandomFromCollection(field.getMobsInRect(rect));
        }
        boolean ravenous = ravenousBat();
        ForceAtomEnum fae = ravenous || chr.hasSkill(BAT_AFFINITY_III) ? ForceAtomEnum.NIGHT_WALKER_BAT_4 : ForceAtomEnum.NIGHT_WALKER_BAT;
        ForceAtomInfo fai = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), 1, 5,
                (int) Util.getAngleOfTwoPositions(chr.getPosition(), mob.getPosition()), 500, Util.getCurrentTime(), 0, 0,
                new Position(shadowBat.getX() - chr.getPosition().getX(), shadowBat.getY() - chr.getPosition().getY()));
        ForceAtom fa = new ForceAtom(false, 0, chr.getId(), fae,
                true, mob.getObjectId(), batAtomSkill(), fai, chr.getRectAround(new Rect(-20, -20, 20, 20)), 0, 0,
                new Position(), 0, new Position(), 0);
        if (!ravenous) {
            // Ravenous Bats hit their target and don't bounce
            fa.setMaxRecreationCount(getShadowBatRecreationCount() + 1);
        }
        chr.createForceAtom(fa);
    }

    private void healByShadowBat() {
        int batSkill = batDataSkill();
        SkillInfo si = SkillData.getSkillInfoById(batSkill);
        int slv = chr.getSkillLevel(batSkill);
        if (si == null || slv <= 0) {
            return;
        }
        chr.heal(chr.getHPPerc(si.getValue(x, slv)));
    }

    // the base bat skill's own value, then what the Bat Affinity passives add (Shadow Bats only)
    private int sumBatStat(SkillStat stat) {
        int batSkill = batDataSkill();
        int total = chr.hasSkill(batSkill) ? SkillData.getSkillInfoById(batSkill).getValue(stat, chr.getSkillLevel(batSkill)) : 0;
        if (ravenousBat()) {
            return total;
        }
        return total + Arrays.stream(batSkills)
                .filter(skill -> skill != SHADOW_BAT && chr.hasSkill(skill))
                .map(skillInt -> SkillData.getSkillInfoById(skillInt).getValue(stat, chr.getSkillLevel(skillInt)))
                .sum();
    }

    private boolean dominionBatsGiven = true;

    // the first throwing star during Dominion fills the (raised) bat limit
    private void dominionBats() {
        if (dominionBatsGiven || dominionBonus(w) <= 0) {
            return;
        }
        dominionBatsGiven = true;
        for (int i = getCurrentShadowBats().size(); i < getMaxShadowBats(); i++) {
            createShadowBat();
        }
    }

    private int getMaxShadowBats() {
        return sumBatStat(y) + dominionBonus(w);
    }

    // a Dominion stat (while it lasts) adds the skill's value
    private int dominionBonus(SkillStat stat) {
        Option o = chr.getTemporaryStatManager().hasStat(Dominion) ? chr.getTemporaryStatManager().getOption(Dominion) : null;
        if (o == null || (o.rOption != DOMINION && o.rOption != HEXA_DOMINION)) {
            return 0;
        }
        return chr.getSkillStatValue(stat, o.rOption);
    }

    private int getShadowBatAttackProp() {
        return sumBatStat(prop);
    }

    private int getShadowBatRecreationCount() {
        return Arrays.stream(batSkills)
                .filter(skill -> chr.hasSkill(skill))
                .map(skillInt -> SkillData.getSkillInfoById(skillInt).getValue(mobCount, chr.getSkillLevel(skillInt)))
                .sum();
    }

    private Skill getCurrentShadowBatSkill() {
        Skill skill = null;
        for (int batSkill : batSkills) {
            if (chr.hasSkill(batSkill)) {
                skill = chr.getSkill(batSkill);
            }
        }
        return skill;
    }

    private void applyDarkElementalOnMob(AttackInfo attackInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (darkElementalOn()) {
            for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                Skill skill = chr.getSkill(DARK_ELEMENTAL);
                SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
                int slv = skill.getCurrentLevel();
                if (Util.succeedProp(getDarkEleProp())) {
                    Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                    if (mob == null) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    Option o1 = new Option();
                    int amount = 1;
                    o1.rOption = DARK_ELEMENTAL;
                    o1.tOption = si.getValue(dotTime, slv) > 0 ? si.getValue(dotTime, slv) : 5;

                    if (mts.hasCurrentMobStat(MobStat.ElementDarkness)) {
                        amount = mts.getCurrentOptionsByMobStat(MobStat.ElementDarkness).nOption;
                        if (amount < getMaxDarkEleStack()) {
                            amount++;
                        }
                    }

                    // if dominion is active, instantly gain max stack
                    if (tsm.getOptByCTSAndSkill(Stance, DOMINION) != null || tsm.getOptByCTSAndSkill(Stance, HEXA_DOMINION) != null) {
                        amount = getMaxDarkEleStack();
                    }

                    o1.nOption = amount;
                    o1.xOption = amount; // the tail value the client keeps with the mark (was always 0)
                    mts.addStatOptionsAndBroadcast(chr, MobStat.ElementDarkness, o1);
                    mts.createAndAddBurnedInfo(chr, mob, skill.getSkillId(), slv, o1.nOption * getDotDmg(), si.getValue(dotInterval, slv), si.getValue(dotTime, slv), 1);

                    //handle Vitality Siphon
                    if (chr.hasSkill(VITALITY_SIPHON)) {
                        incrementSiphonVitality(tsm);
                    }
                }
            }
        }
    }

    // v271 Dark Elemental is passive: the mark procs once the skill is learned (the client has no ElementDarkness buff)
    private boolean darkElementalOn() {
        return chr.hasSkill(DARK_ELEMENTAL);
    }

    private int getDotDmg() {
        int dotDmg = 0;
        for (int darkEleSkill : darkEleSkills) {
            if (chr.hasSkill(darkEleSkill)) {
                Skill skill = chr.getSkill(darkEleSkill);
                SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
                int slv = skill.getCurrentLevel();
                dotDmg += si.getValue(dot, slv);
            }
        }
        return dotDmg;
    }

    private int getMaxDarkEleStack() {
        int maxStack = 0;
        for (int darkEleSkill : darkEleSkills) {
            if (chr.hasSkill(darkEleSkill)) {
                Skill skill = chr.getSkill(darkEleSkill);
                SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
                int slv = skill.getCurrentLevel();
                maxStack += si.getValue(x, slv);
            }
        }
        return maxStack;
    }

    private Skill getDarkElementalSkill() {
        Skill skill = null;
        for (int darkEleSkill : darkEleSkills) {
            if (chr.hasSkill(darkEleSkill)) {
                skill = chr.getSkill(darkEleSkill);
            }
        }
        return skill;
    }

    private int getDarkEleProp() {
        int proc = 0;
        for (int darkEleSkill : darkEleSkills) {
            if (chr.hasSkill(darkEleSkill)) {
                Skill skill = chr.getSkill(darkEleSkill);
                SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
                int slv = skill.getCurrentLevel();
                proc += si.getValue(prop, slv);
            }
        }
        return proc;
    }

    private void incrementSiphonVitality(TemporaryStatManager tsm) {
        Option o = new Option();
        Option o1 = new Option();
        Option o2 = new Option();
        Skill skill = chr.getSkill(VITALITY_SIPHON);
        SkillInfo si = SkillData.getSkillInfoById(VITALITY_SIPHON);
        int slv = skill.getCurrentLevel();
        int amount = 1;
        if (darkElementalOn()) {
            if (tsm.hasStat(SiphonVitality)) {
                amount = tsm.getOption(SiphonVitality).nOption;
                if (amount < getMaxSiphon()) {
                    amount++;
                }
            }

            if (amount >= getMaxSiphon()) {
                tsm.removeStatsBySkill(VITALITY_SIPHON);
                o.nOption = si.getValue(y, slv) + chr.getSkillStatValue(x, VITALITY_SIPHON_EXTRA_POINT);
                o.rOption = VITALITY_SIPHON_SHIELD;
                o.tOption = si.getValue(subTime, slv);
                tsm.putCharacterStatValue(ProtectiveShield, o, true);
                if (chr.hasSkill(VITALITY_SIPHON_PREPARATION)) {
                    o1.nOption = chr.getSkillStatValue(x, VITALITY_SIPHON_PREPARATION);
                    o1.rOption = VITALITY_SIPHON_SHIELD;
                    o1.tOption = si.getValue(subTime, slv);
                    tsm.putCharacterStatValue(IndieAsrR, o1);
                }
                if (chr.hasSkill(VITALITY_SIPHON_REINFORCE)) {
                    o2.nOption = chr.getSkillStatValue(x, VITALITY_SIPHON_REINFORCE);
                    o2.rOption = VITALITY_SIPHON_SHIELD;
                    o2.tOption = si.getValue(subTime, slv);
                    tsm.putCharacterStatValue(IndiePAD, o2);
                }
                tsm.sendSetStatPacket();
                return;
            }

            o.nOption = amount;
            o.rOption = skill.getSkillId();
            o.tOption = si.getValue(time, slv);
            tsm.putCharacterStatValue(SiphonVitality, o);
            tsm.sendSetStatPacket();
        }
    }

    private int getMaxSiphon() {
        Skill skill = null;
        if (chr.hasSkill(VITALITY_SIPHON)) {
            skill = chr.getSkill(VITALITY_SIPHON);
        }
        return skill == null ? 0 : SkillData.getSkillInfoById(skill.getSkillId()).getValue(x, skill.getCurrentLevel());
    }

    private void placeShadowSpearAA(AttackInfo attackInfo) {
        Skill skill = chr.getSkill(SHADOW_SPEAR);
        if (skill != null) {
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int slv = skill.getCurrentLevel();
            Rect rect = new Rect(
                    new Position(
                            -20,
                            -20),
                    new Position(
                            20,
                            20)
            );
            for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                AffectedArea aa = AffectedArea.getPassiveAA(chr, SHADOW_SPEAR_AA_SMALL, slv);
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                if (mob == null) {
                    continue;
                }
                int randInt = new Random().nextInt(140) - 70;
                Position randPos = new Position(mob.getPosition().getX() + randInt, mob.getPosition().getY());
                if (canSpawnGiantShadowSpear(mob.getPosition())
                        && !chr.hasSkillOnCooldown(SHADOW_SPEAR_AA_LARGE)
                        && (getGiantShadowSpearAA() == null
                        || (getGiantShadowSpearAA() != null && !getGiantShadowSpearAA().getRect().hasPositionInside(randPos)))) {
                    removeShadowSpearAfterGiant(mob.getPosition());
                    chr.write(UserLocal.giantShadowSpearAttack(randPos));
                    // the giant spear's cooldown: t (3.9) sec, which the data also has as subTime in ms
                    chr.addSkillCoolTime(SHADOW_SPEAR_AA_LARGE, si.getValue(subTime, slv));
                } else {
                    aa.setPosition(randPos);
                    aa.setFh(chr.getField().findFootHoldIdBelow(mob.getPosition()));
                    aa.setRect(aa.getPosition().getRectAround(rect));
                    aa.setTemplateId(SHADOW_SPEAR_AA_SMALL);
                    aa.setNoFoothold(true);
                    aa.setDuration(si.getValue(subTime, slv));
                    chr.getField().spawnAffectedArea(aa);
                }
            }
        }
    }

    // the v265 data spawned the small spears as server areas; v271 dropped that skill
    private static boolean smallSpearsByServer() {
        return SkillData.getSkillInfoById(SHADOW_SPEAR_AA_SMALL) != null;
    }

    private boolean canSpawnGiantShadowSpear(Position position) {
        return chr.getField().getAffectAreasInRect(position.getRectAround(new Rect(-100, -50, 100, 50))).stream()
                .filter(aa -> aa.getSkillID() == SHADOW_SPEAR_AA_SMALL && aa.getOwner() == chr)
                .count() > 15;
    }

    private void removeShadowSpearAfterGiant(Position position) {
        chr.getField().getAffectAreasInRect(position.getRectAround(new Rect(-100, -50, 100, 50))).stream()
                .filter(aa -> aa.getSkillID() == SHADOW_SPEAR_AA_SMALL && aa.getOwner() == chr)
                .forEach(aa -> chr.getField().removeLife(aa));
        setGiantShadowSpearAA(position);
    }

    private void setGiantShadowSpearAA(Position position) {
        SkillInfo si = SkillData.getSkillInfoById(SHADOW_SPEAR);
        AffectedArea aa = AffectedArea.getPassiveAA(chr, SHADOW_SPEAR_AA_LARGE, 1);
        aa.setPosition(position);
        aa.setRect(position.getRectAround(new Rect(-100, -50, 100, 50)));
        aa.setDuration(si.getValue(subTime, chr.getSkillLevel(SHADOW_SPEAR)));
        chr.getField().spawnAffectedAreaAndRemoveOld(aa);
    }

    private AffectedArea getGiantShadowSpearAA() {
        return chr.getField().getAffectedAreas().stream().filter(aa -> aa.getSkillID() == SHADOW_SPEAR_AA_LARGE && aa.getOwner() == chr).findFirst().orElse(null);
    }

    private int shadowBiteSkill() {
        return chr.hasSkill(HEXA_SHADOW_BITE) ? HEXA_SHADOW_BITE : SHADOW_BITE;
    }

    private void createShadowBiteForceAtom(AttackInfo attackInfo) {
        if (!chr.hasSkill(shadowBiteSkill())) {
            return;
        }
        Field field = chr.getField();
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            Mob mob = mai.mob;
            if (mob == null) {
                continue;
            }
            Rect rect = new Rect(
                    new Position(
                            -500,
                            -500),
                    new Position(
                            500,
                            500)
            );
            if (mai.mobDies || mob.isBoss()) {
                // If mobs die, create ShadowBite atom
                ForceAtomEnum fae = ForceAtomEnum.SHADOW_BITE;
                ForceAtomInfo forceAtomInfo = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), 3, 2,
                        0, 1900, Util.getCurrentTime(), 1, 0,
                        new Position());
                forceAtomInfo.setBossMob(mob.isBoss());
                chr.createForceAtom(new ForceAtom(true, chr.getId(), mob.getObjectId(), fae,
                        false, mob.getObjectId(), shadowBiteSkill(), forceAtomInfo, mob.getPosition().getRectAround(rect), 0, 300,
                        mob.getPosition(), 0, mob.getPosition(), 0), false);
            }

            Mob toMob = Util.getRandomFromCollection(field.getMobsInRect(mob.getPosition().getRectAround(rect)));
            if (toMob == null) {
                continue;
            }
            ForceAtomEnum fae = chr.hasSkill(BAT_AFFINITY_III) ? ForceAtomEnum.NIGHT_WALKER_FROM_MOB_4 : ForceAtomEnum.NIGHT_WALKER_FROM_MOB;
            ForceAtomInfo fai = new ForceAtomInfo(chr.getNewForceAtomKey(), fae.getInc(), 1, 5,
                    (int) Util.getAngleOfTwoPositions(mob.getPosition(), toMob.getPosition()), 0, Util.getCurrentTime(), 0, 0,
                    new Position());
            ForceAtom fa = new ForceAtom(true, chr.getId(), mob.getObjectId(), fae,
                    true, toMob.getObjectId(), hexaBat() ? HEXA_SHADOW_BAT_FROM_MOB_ATOM : SHADOW_BAT_FROM_MOB_ATOM, fai,
                    mob.getPosition().getRectAround(rect), 0, 0, new Position(),
                    hexaBat() ? HEXA_SHADOW_BAT_FROM_MOB_ATOM : SHADOW_BAT_FROM_MOB_ATOM, mob.getPosition(), 0);
            fa.setMaxRecreationCount(3);
            chr.createForceAtom(fa);
        }
    }

    private void giveShadowBiteBuff(boolean isBoss) {
        int biteSkill = shadowBiteSkill();
        if (!chr.hasSkill(biteSkill)) {
            return;
        }
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        Skill skill = chr.getSkill(biteSkill);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        int count = isBoss ? si.getValue(w, slv) : si.getValue(y, slv);
        if (tsm.getOptByCTSAndSkill(IndiePMdR, biteSkill) != null) {
            count = tsm.getOptByCTSAndSkill(IndiePMdR, biteSkill).nOption;
            if (count < si.getValue(q, slv)) {
                if (isBoss) {
                    count += si.getValue(w, slv);
                } else {
                    count += si.getValue(y, slv);
                }
            }
        }
        o.nOption = count > si.getValue(q, slv) ? si.getValue(q, slv) : count;
        o.rOption = biteSkill;
        o.tOption = si.getValue(time, slv);
        tsm.putCharacterStatValue(IndiePMdR, o);
        tsm.sendSetStatPacket();
    }

    public void handleForceAtomCollision(ForceAtom fa, ForceAtomCollisionInfo faci) {
        var skillId = faci.skillId;
        var faKey = faci.forceAtomKey;

        switch (skillId) {
            case SHADOW_BITE:
            case HEXA_SHADOW_BITE:
                ForceAtomInfo fai = fa.getFaiList().stream().filter(sfai -> sfai.getKey() == faKey).findFirst().orElse(null);
                if (fai != null) {
                    boolean isBoss = fai.isBossMob();
                    giveShadowBiteBuff(isBoss);
                }
                break;
        }

        super.handleForceAtomCollision(fa, faci);
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillId);
        }
        Summon summon;
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillId) {
            case SHADOW_BAT_SKILL:
            case HEXA_SHADOW_BAT_SKILL:
            case RAVENOUS_BAT_SKILL:
            case HEXA_RAVENOUS_BAT_SKILL:
                // the client asks for a bat itself ("User Summon"); unanswered, it repeats and holds its other actions
                if (batToggle() != 0 && getCurrentShadowBats().size() < getMaxShadowBats()) {
                    createShadowBat();
                }
                chr.dispose();
                break;
            case SILENCE_BAT: // Origin follow-up summon, when the client uses its id
                if (chr.getSkillLevel(SILENCE) > 0) {
                    Summon originSummon = Summon.getSummonBy(chr, SILENCE_BAT, chr.getSkillLevel(SILENCE));
                    originSummon.setFlyMob(false);
                    originSummon.setMoveAbility(net.swordie.ms.enums.MoveAbility.Stop);
                    originSummon.setAssistType(net.swordie.ms.enums.AssistType.Attack);
                    chr.getField().spawnSummonAndRemoveOld(originSummon);
                }
                break;
            case DARK_EVASION:
                // invincible for time sec; the client keeps the skill lock itself
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                break;
            case DARK_ELEMENTAL:
                // v271: a passive mark; casting only shows the elemental, it no longer toggles anything
                break;
            case HASTE:
                o1.nOption = si.getValue(speed, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Speed, o1);
                o2.nOption = si.getValue(jump, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Jump, o2);
                break;
            case DARK_SIGHT:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(DarkSight, o1);
                darkSightCDCount = 0;
                break;
            case DARK_SIGHT_V271:
                // what the v271 client casts for Dark Sight: GMS answers DarkSight n = the skill level, its own time
                o1.nOption = slv;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(DarkSight, o1);
                darkSightCDCount = 0;
                break;
            case THROWING_BOOSTER:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case DARK_SERVANT:
                // the buff the client (and a pet's auto-buff) checks; without it the pet recasts every second
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ShadowServant, o1);
                applyDarkServant();
                break;
            case SPIRIT_PROJECTION:
                // "infinite throwing stars": the buff (and its icon) the client expects, with the stars it was cast with
                o1.nOption = skillUseInfo != null && skillUseInfo.projectileItemId != 0
                        ? skillUseInfo.projectileItemId : chr.getBulletIDForAttack();
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(NoBulletConsume, o1);
                // throwing stars are never used up on this server, so like Night Lord's Shadow Stars (its
                // counterpart) it also gives a final damage bonus for its duration
                if (SkillChangeConstants.SHADOW_STARS_CUSTOM_BUFF) {
                    o2.nOption = SkillChangeConstants.SHADOW_STARS_FINAL_DAMAGE;
                    o2.rOption = skillId;
                    o2.tOption = si.getValue(time, slv);
                    tsm.putCharacterStatValue(IndiePMdR, o2);
                }
                break;
            case DARKNESS_ASCENDING:
                // on/off: stays until toggled off or used up by a fatal hit
                if (tsm.getOptByCTSAndSkill(ReviveOnce, skillId) != null) {
                    tsm.removeStatsBySkill(skillId);
                    tsm.sendResetStatPacket();
                } else {
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    tsm.putCharacterStatValue(ReviveOnce, o1, true);
                }
                break;
            case SHADOW_BAT:
            case HEXA_SHADOW_BAT:
            case RAVENOUS_BAT:
            case HEXA_RAVENOUS_BAT:
                // one bat toggle at a time: casting the one that is on turns it off, another one swaps
                int current = batToggle();
                if (current != 0) {
                    tsm.removeStatsBySkill(current);
                    tsm.sendResetStatPacket();
                    getCurrentShadowBats().forEach(field::removeSummon);
                    Summon enhanced = getEnhancedBat();
                    if (enhanced != null) {
                        field.removeSummon(enhanced);
                    }
                    batsSinceEnhanced = 0;
                }
                if (current != skillId) {
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    tsm.putCharacterStatValue(NightWalkerBat, o1);
                }
                break;
            case GLORY_OF_THE_GUARDIANS_NW:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case SHADOW_ILLUSION:
                Summon servant = field.getSummonByChrAndSkillId(chr, DARK_SERVANT);
                servantBeforeIllusion = servant != null;
                if (servant != null) {
                    // keep the ShadowServant buff (or a pet's auto-buff recasts it); only the summon goes
                    Option summonOpt = tsm.getOptByCTSAndSkill(IndieSummon, DARK_SERVANT);
                    if (summonOpt != null) {
                        tsm.removeIndieStat(IndieSummon, summonOpt);
                        tsm.sendResetStatPacket();
                    }
                    field.removeSummon(servant);
                }
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ShadowIllusion, o1);
                for (int i = skillId; i < skillId + 3; i++) {
                    summon = Summon.getSummonBy(chr, i, slv);
                    summon.setAvatarLook(chr.getAvatarData().getAvatarLook());
                    summon.setAvatarLook(chr.getAvatarData().getAvatarLook());
                    summon.setMoveAbility(MoveAbility.WalkClone);
                    summon.setAssistType(AssistType.None);
                    summon.setActionDelay((i - (skillId - 1)) * 400);
                    summon.setMovementDelay((i - (skillId - 1)) * 30);
                    summon.setFlip(true);
                    field.spawnSummonAndRemoveOld(summon);
                }
                break;
            case DARK_OMEN:
            case HEXA_DARK_OMEN:
            case DARK_OMEN_WEAKENED:
            case HEXA_DARK_OMEN_WEAKENED:
            case DARK_OMEN_V271:
            case HEXA_DARK_OMEN_V271:
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setFlyMob(false);
                summon.setMoveAbility(MoveAbility.Stop);
                if (skillUseInfo != null) {
                    summon.setClientEffectIds(skillUseInfo.effectIds.clone());
                }
                field.spawnSummonAndRemoveOld(summon);
                break;
            case SHADOW_SPEAR:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                // v271 data has no small-spear area skill: the client forms the spears from its ShadowSpear stat
                tsm.putCharacterStatValue(smallSpearsByServer() ? HiddenPossession : ShadowSpear, o1);
                break;
            case GREATER_DARK_SERVANT:
            case HEXA_GREATER_DARK_SERVANT:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ShadowPartner, o1);
                break;
            case SHADOW_SLIDE:
            case HEXA_SHADOW_SLIDE:
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setFlyMob(false);
                summon.setAvatarLook(chr.getAvatarData().getAvatarLook());
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setCount(si.getValue(w, slv));
                field.spawnSummonAndRemoveOld(summon);
                break;
        }
        tsm.sendSetStatPacket();
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (hitInfo.hpDamage > 0 && tsm.hasStatBySkillId(DARK_SIGHT)) {
            // TODO     Check if hp% attack
            incrementDarkSightCooltime();
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    private void incrementDarkSightCooltime() {
        Skill skill = chr.getSkill(DARK_SIGHT);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();
        int maxStack = si.getValue(y, slv);
        if (darkSightCDCount < maxStack) {
            darkSightCDCount++;
        }
    }

    public void reviveByDarknessAscending() {
        Field field = chr.getField();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!chr.hasSkill(DARKNESS_ASCENDING)) {
            return;
        }
        Skill skill = chr.getSkill(DARKNESS_ASCENDING);
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        int slv = skill.getCurrentLevel();

        tsm.removeStatsBySkill(skill.getSkillId());
        tsm.sendResetStatPacket();
        ascendingRevive = true;
        try {
            chr.setSkillCooldown(skill.getSkillId(), slv); // hardcode-ok
        } finally {
            ascendingRevive = false;
        }
        chr.write(UserPacket.effect(Effect.skillPreLoopEnd(skill.getSkillId(), si.getValue(subTime, slv) * 1000)));

        int healedR = si.getValue(x, slv) + (getCurrentShadowBats().size() * si.getValue(w, slv));
        chr.heal(chr.getHPPerc(healedR), false, true);
        getCurrentShadowBats().forEach(field::removeLife);

        Option o = new Option();
        Option o1 = new Option();
        o.nOption = 1;
        o.rOption = skill.getSkillId();
        o.tOption = si.getValue(subTime, slv);
        tsm.putCharacterStatValue(NotDamaged, o);
        o1.nOption = si.getValue(z, slv);
        o1.rOption = skill.getSkillId();
        o1.tOption = si.getValue(subTime, slv);
        tsm.putCharacterStatValue(DarknessAscension, o1);
        tsm.sendSetStatPacket();
        chr.chatMessage("You have been revived by Darkness Ascending.");
    }

    private int getOriginalByFinisherId(int skillId) {
        switch (skillId) {
            case QUAD_STAR_FINISHER:
                return QUAD_STAR;
            case TRIPLE_THROW_FINISHER:
                return TRIPLE_THROW;
            case QUINTUPLE_STAR_FINISHER:
                return QUINTUPLE_STAR;
            case SHADOW_SPARK_FINISHER:
                return SHADOW_SPARK;
            default:
                return skillId;
        }
    }
}
