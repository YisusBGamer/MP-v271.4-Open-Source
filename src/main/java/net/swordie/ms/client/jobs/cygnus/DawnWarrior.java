package net.swordie.ms.client.jobs.cygnus;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.SkillStat;
import net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.MobAttackInfo;
import net.swordie.ms.client.character.skills.info.SecondAtomInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.Summoned;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.QuestConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.handlers.executors.EventManager;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.life.mob.MobTemporaryStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Position;
import net.swordie.ms.util.Rect;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class DawnWarrior extends Noblesse {

    public static final int ELEMENTAL_HARMONY_STR = 10000246;

    public static final int SOUL_ELEMENT = 11001022; // toggle, Cosmic Orb gain
    public static final int SOUL_ELEMENT_BUFF = 11001027; // attack on orb creation
    public static final int SOUL_ELEMENT_ORB = 11001030;
    public static final int FALLING_MOON = 11001024; // stance, follows Rising Sun's level
    public static final int RISING_SUN = 11001025; // stance

    public static final int COSMIC_MATTER = 11101029;
    public static final int EQUINOX_CYCLE = 11100031; // v271 (v265 toggle 11101031)
    public static final int EQUINOX_CYCLE_MOON = 11101032;
    public static final int EQUINOX_CYCLE_SUN = 11101033;
    public static final int SOUL_BLESSING = 11100034;

    public static final int TRUE_SIGHT = 11111023; //Buff (Mob Def Debuff & Final DmgUp Debuff)
    public static final int TRUE_SIGHT_PERSIST = 11120043;
    public static final int TRUE_SIGHT_GUARDBREAK = 11120045;
    public static final int TRUE_SIGHT_ENHANCE = 11120044;
    public static final int WILL_OF_STEEL = 11110025;
    public static final int COSMIC_SHOWER = 11111029;
    public static final int SOUL_BLESSING_II = 11110031;
    public static final int EQUINOX_MASTERY = 11110032;
    public static final int EQUINOX_MASTERY_SUN = 11110033;

    public static final int IMPALING_RAYS = 11121004; //Special Attack (Incapacitate Debuff)
    public static final int IMPALING_RAYS_EXPLOSION = 11121013;
    public static final int CALL_OF_CYGNUS_DW = 11121000;
    public static final int MASTER_OF_THE_SWORD = 11120009;
    public static final int MASTER_OF_THE_SWORD_SUN = 11120010;
    public static final int EQUINOX_SLASH = 11121014;
    public static final int COSMIC_BURST = 11121018;
    public static final int SOUL_BLESSING_III = 11120019;
    public static final int COSMIC_SHOWER_PERSIST = 11120048;

    public static final int SOUL_FORGE = 11121054; // Cosmic Forge
    public static final int GLORY_OF_THE_GUARDIANS_DW = 11121053;
    public static final int BLAZING_ASSAULT = 11121257;
    public static final int LUSTER_CHARGE = 11121157;

    // V Skills
    public static final int CELESTIAL_DANCE = 400011005;
    public static final int CELESTIAL_DANCE_MOON = 400011022;
    public static final int CELESTIAL_DANCE_SUN = 400011023;
    public static final int RIFT_OF_DAMNATION = 400011055;
    public static final int RIFT_OF_DAMNATION_ATTACK = 400011056; // Styx Crossing
    public static final int RIFT_OF_DAMNATION_SUMMON = 400011065;
    public static final int SOUL_ECLIPSE = 400011088;
    public static final int SOUL_ECLIPSE_BREAK = 400011089;
    public static final int COSMOS = 400011142;

    public static final int FLARE_SLASH_SUN = 400011048;
    public static final int FLARE_SLASH_MOON = 400011049;

    // HEXA Skills
    public static final int ASTRAL_BLITZ = 11141500;
    public static final int ASTRAL_BLITZ_BUFF = 11141501;
    public static final int HEXA_LUNA_DIVIDE = 11141100;
    public static final int HEXA_SOLAR_SLASH = 11141200;
    public static final int HEXA_COSMIC_SHOWER = 11141002;
    public static final int HEXA_COSMIC_BURST = 11141004;
    public static final int HEXA_EQUINOX_SLASH = 11141005;
    public static final int EQUINOX_POWER = 11141003; // the learned HEXA skill, fires by itself
    public static final int EQUINOX_POWER_EXTRA = 11141000;
    public static final int EQUINOX_POWER_SUN = 11141001;
    public static final int EQUINOX_POWER_II = 11141006;


    private static final int SOUL_ECLIPSE_INVINCIBLE_SEC = 2; // hardcode-ok: "invincible while casting", no stat
    private static final int SUMMON_CAST_INVINCIBLE_SEC = 2; // hardcode-ok
    private static final int MOON_BUCKSHOT = 10; // hardcode-ok: client value

    private static final int[] addedSkills = new int[]{
            ELEMENTAL_HARMONY_STR
    };

    private ScheduledFuture willOfSteelTimer;
    // stance family of the last Solar/Luna hit; orbs come from switching between them
    private Boolean lastHitLuna;
    private boolean removingElementSoul;
    private static final int COSMIC_BURST_RANGE = 700;

    public DawnWarrior(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id) || chr.getSkill(id).getCurrentLevel() == 0) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            removeSkillBeforeFourthJob(CALL_OF_CYGNUS_DW);
            startWillOfSteel(chr);
        }
    }

    private void startWillOfSteel(Char chr) {
        if (!chr.hasSkill(WILL_OF_STEEL)) {
            return;
        }

        var interval = 5;
        var si = SkillData.getSkillInfoById(WILL_OF_STEEL);
        if (si != null && chr.hasSkill(WILL_OF_STEEL) && chr != null && chr.getField() != null) {
            var skill = chr.getSkill(WILL_OF_STEEL);
            var slv = skill.getCurrentLevel();
            interval = si.getValue(w, slv);
        }

        EventManager.stopTimer(willOfSteelTimer);
        if (interval > 0) {
            willOfSteelTimer = EventManager.addFixedRateEvent(this::willOfSteel, interval, interval, TimeUnit.SECONDS);
        }
    }

    @Override
    public net.swordie.ms.client.character.skills.BypassCooldownCheckType canBypassCooldownCheck(int skillId,
            AttackInfo attackInfo, SkillUseInfo sui, net.swordie.ms.client.character.skills.SkillUseSource source) {
        // the galaxy's hits come in as Cosmos attacks while its cooldown (set by the cast) runs
        if (skillId == COSMOS && source.isAttackRequest() && chr.getTemporaryStatManager().hasStat(Cosmos)) {
            return net.swordie.ms.client.character.skills.BypassCooldownCheckType.BypassCheckAndCooldown;
        }
        return super.canBypassCooldownCheck(skillId, attackInfo, sui, source);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isDawnWarrior(id);
    }


    private void willOfSteel() {
        if (chr.hasSkill(WILL_OF_STEEL) && chr != null && chr.getField() != null) {
            Skill skill = chr.getSkill(WILL_OF_STEEL);
            int slv = skill.getCurrentLevel();
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int heal = (int) ((double) (chr.getMaxHP() * si.getValue(y, slv)) / 100F);

            chr.heal(heal, true, false);
        } else if (willOfSteelTimer != null) {
            willOfSteelTimer.cancel(true);
        }
    }

    private boolean isSkillLocked(int skillId) {
        Quest quest = chr.getQuestManager().getQuestById(QuestConstants.SKILL_COMMAND_LOCK_QUEST_ID_2);
        return quest != null && quest.hasProperty(String.valueOf(skillId));
    }

    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        handleDawnAttack(c, attackInfo);
    }

    // Equinox Power: a Solar/Luna hit with it off cooldown fires the sun and moon blades (+ Equinox Power II's atoms)
    private void equinoxPower() {
        if (!chr.hasSkill(EQUINOX_POWER) || chr.hasSkillOnCooldown(EQUINOX_POWER)) {
            return;
        }
        int slv = chr.getSkillLevel(EQUINOX_POWER);
        chr.setSkillCooldown(EQUINOX_POWER, slv); // hardcode-ok
        chr.write(net.swordie.ms.connection.packet.field.FieldPacket.registerExtraSkill(EQUINOX_POWER_EXTRA,
                new net.swordie.ms.client.character.skills.ExtraSkillInfo(EQUINOX_POWER_EXTRA, chr.getPosition(), chr.isLeft(), 0, 1)));
        chr.write(UserLocal.userBonusAttackRequest(EQUINOX_POWER_SUN));
        if (!chr.hasSkill(EQUINOX_POWER_II)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(EQUINOX_POWER_II);
        if (si == null || si.getSecondAtomInfos().isEmpty()) {
            return;
        }
        int count = Math.max(1, si.getValue(bulletCount, chr.getSkillLevel(EQUINOX_POWER_II)));
        java.util.List<net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom> atoms = new java.util.ArrayList<>();
        for (int i = 0; i < count; i++) {
            var sai = si.getSecondAtomInfos().get(0);
            var atom = net.swordie.ms.client.character.skills.atom.secondatom.SecondAtom.createFromInfo(chr, EQUINOX_POWER_II, sai);
            atom.setKey(i);
            atoms.add(atom);
        }
        chr.spawnSecondAtoms(atoms);
    }

    private void handleDawnAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
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
        boolean luna = SkillConstants.isLunaSkill(attackInfo.skillId);
        boolean solar = SkillConstants.isSolarSkill(attackInfo.skillId);

        // Cosmic Orbs
        if (hasHitMobs && attackInfo.bySummonedID == 0 && (luna || solar || attackInfo.skillId == RIFT_OF_DAMNATION_ATTACK)) {
            if (attackInfo.skillId == RIFT_OF_DAMNATION_ATTACK) {
                addCosmicOrbs(getMaxCosmicOrbs());
            } else {
                if (lastHitLuna != null && lastHitLuna != luna && Util.succeedProp(getCosmicOrbProp())) {
                    addCosmicOrbs(1);
                }
                lastHitLuna = luna;
            }
            cosmicBurstAuto();
            equinoxPower();
        }

        // Celestial Dance
        if (tsm.hasStatBySkillId(CELESTIAL_DANCE)
                && !chr.hasSkillOnCooldown(CELESTIAL_DANCE_SUN)
                && !chr.hasSkillOnCooldown(CELESTIAL_DANCE_MOON)
                && attackInfo.skillId != CELESTIAL_DANCE_SUN
                && attackInfo.skillId != CELESTIAL_DANCE_MOON
                && attackInfo.skillId != SOUL_ECLIPSE
                && attackInfo.skillId != SOUL_ECLIPSE_BREAK) {
            if (tsm.getOption(PoseType).nOption == 1) { // Moon Attack
                chr.write(UserLocal.userBonusAttackRequest(CELESTIAL_DANCE_MOON));
            } else if (tsm.getOption(PoseType).nOption == 2) { // Sun Attack
                chr.write(UserLocal.userBonusAttackRequest(CELESTIAL_DANCE_SUN));
            }
            chr.addSkillCoolTime(CELESTIAL_DANCE_MOON, 5000);
            chr.addSkillCoolTime(CELESTIAL_DANCE_SUN, 5000);
        }

        // Equinox Cycle: a Luna skill turns the stance to Rising Sun, a Solar skill to Falling Moon
        if (attackInfo.bySummonedID == 0 && tsm.hasStat(GlimmeringTime)
                && attackInfo.skillId != BLAZING_ASSAULT && attackInfo.skillId != LUSTER_CHARGE) {
            if (luna && tsm.getOption(PoseType).nOption != 2) {
                applyStance(false);
                reduceFlareSlash(x);
            } else if (solar && tsm.getOption(PoseType).nOption != 1) {
                applyStance(true);
                reduceFlareSlash(x);
            }
        }

        if (attackInfo.bySummonedID == CELESTIAL_DANCE) {
            reduceFlareSlash(y);
        }

        // Flare Slash
        if (chr.hasSkill(FLARE_SLASH_SUN) && !chr.hasSkillOnCooldown(FLARE_SLASH_SUN) && tsm.hasStat(PoseType)) {
            if (tsm.getOption(PoseType).nOption == 1) {
                // Moon
                chr.write(UserLocal.userBonusAttackRequest(FLARE_SLASH_MOON));
            } else {
                // Sun
                chr.write(UserLocal.userBonusAttackRequest(FLARE_SLASH_SUN));
            }
        }

        Option o1 = new Option();
        Option o2 = new Option();
        switch (attackInfo.skillId) {
            case IMPALING_RAYS:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);
                o2.nOption = 1;
                o2.rOption = skill.getSkillId();
                o2.wOption = chr.getId();
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        if (mob == null) {
                            continue;
                        }
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        mts.addStatOptionsAndBroadcast(chr, MobStat.Freeze, o1);
                        mts.addStatOptionsAndBroadcast(chr, MobStat.SoulExplosion, o2);
                    }
                }
                break;
            case RIFT_OF_DAMNATION_ATTACK:
                reduceFlareSlash(z);
                summonAndIncRift(attackInfo);
                break;
            case RIFT_OF_DAMNATION_SUMMON:
                summonAndIncRift(attackInfo);
                break;
            case SOUL_ECLIPSE_BREAK:
                if (chr.getField().getSummonByChrAndSkillId(chr, SOUL_ECLIPSE) != null) {
                    chr.getField().removeSummon(chr.getField().getSummonByChrAndSkillId(chr, SOUL_ECLIPSE));
                    o1.nOption = 1;
                    o1.rOption = SOUL_ECLIPSE;
                    o1.tOption = SOUL_ECLIPSE_INVINCIBLE_SEC;
                    tsm.putCharacterStatValue(IndieNotDamaged, o1, true); // Invincibility
                }
                break;
            case BLAZING_ASSAULT:
            case LUSTER_CHARGE:
                if (si != null && !tsm.hasStatBySkillId(attackInfo.skillId)) {
                    o1.nOption = 1;
                    o1.rOption = attackInfo.skillId;
                    o1.tOption = si.getValue(x, slv);
                    o1.setInMillis(true);
                    tsm.putCharacterStatValue(IndieNotDamaged, o1, true);
                }
                break;
            case ASTRAL_BLITZ:
                if (!tsm.hasStatBySkillId(ASTRAL_BLITZ_BUFF)) {
                    SkillInfo buffSi = SkillData.getSkillInfoById(ASTRAL_BLITZ_BUFF);
                    if (buffSi != null) {
                        o1.nOption = buffSi.getValue(y, slv);
                        o1.rOption = ASTRAL_BLITZ_BUFF;
                        o1.tOption = buffSi.getValue(time, slv);
                        tsm.putCharacterStatValue(IndiePMdR, o1);
                    }
                }
                break;
        }
        tsm.sendSetStatPacket();

        super.handleAttack(c, attackInfo);
    }

    private void reduceFlareSlash(SkillStat skillStat) {
        var skill = chr.getSkill(FLARE_SLASH_SUN);
        if (skill == null || !chr.hasSkillOnCooldown(skill.getSkillId())) {
            return;
        }

        // x = 800ms    |  Changing Stance through Equinox
        // y = 900ms    |  Celestial Dance Afterimage Attacks
        // z = 400ms    |  Styx Crossing Hits
        var si = SkillData.getSkillInfoById(skill.getSkillId());
        var slv = skill.getCurrentLevel();

        chr.reduceSkillCoolTime(skill.getSkillId(), si.getValue(skillStat, slv));
    }

    private void summonAndIncRift(AttackInfo attackInfo) {
        Field field = chr.getField();
        Rect rect = chr.getRectAround(new Rect(-300, -300, 300, 300));
        int curFieldRiftCount = (int) field.getSummons().stream().filter(s -> s.getSkillID() == RIFT_OF_DAMNATION_SUMMON && s.getChr() == chr).count();
        SkillInfo si = SkillData.getSkillInfoById(RIFT_OF_DAMNATION);
        int slv = chr.getSkillLevel(RIFT_OF_DAMNATION);
        Summon riftInRect = field.getSummonByChrAndSkillIdInRect(chr, RIFT_OF_DAMNATION_SUMMON, rect);
        switch (attackInfo.skillId) {
            case RIFT_OF_DAMNATION_ATTACK:
                if (riftInRect == null) {
                    if (curFieldRiftCount >= 2 || chr.hasSkillOnCooldown(RIFT_OF_DAMNATION_SUMMON)) {
                        return;
                    }
                    Summon summon = Summon.getSummonByNoCTS(chr, RIFT_OF_DAMNATION_SUMMON, slv);
                    summon.setState(1);
                    summon.setFlip(!attackInfo.left);
                    summon.setMoveAbility(MoveAbility.Stop);
                    field.spawnSummon(summon);
                } else {
                    field.broadcastPacket(Summoned.stateChanged(riftInRect, 1, new HashMap<>()));
                }
                break;
            case RIFT_OF_DAMNATION_SUMMON:
                if (riftInRect != null) {
                    field.removeSummon(riftInRect);
                    chr.addSkillCoolTime(attackInfo.skillId, si.getValue(z, slv) * 1000);
                }
                break;
        }
    }

    public void handleRemoveCTS(CharacterTemporaryStat cts, Option o) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (cts == GlimmeringTime) {
            tsm.removeStatsBySkill(EQUINOX_CYCLE_MOON);
            tsm.removeStatsBySkill(EQUINOX_CYCLE_SUN);
        } else if (cts == LightOfSpirit) {
            // Cosmic Forge's extra orb slots end with it
            int max = getMaxCosmicOrbs() - getCosmicForgeOrbs();
            if (getCosmicOrbs() > max) {
                setCosmicOrbs(max, false);
            }
        } else if (cts == ElementSoul) {
            removingElementSoul = true;
            tsm.removeStatsBySkill(SOUL_ELEMENT_ORB);
            tsm.removeStatsBySkill(SOUL_ELEMENT_BUFF);
            removingElementSoul = false;
        } else if (cts == CosmicOrb && !removingElementSoul && tsm.hasStat(ElementSoul)) {
            updateElementSoul(0);
        }
        super.handleRemoveCTS(cts, o);
    }

    // Stances ---------------------------------------------------------------------------------------------------------

    // Master of the Sword > Equinox Mastery > Equinox Cycle > base stance: each overwrites the one below
    private int[] getStanceSkill(boolean moon) {
        if (chr.hasSkill(MASTER_OF_THE_SWORD)) {
            return new int[]{moon ? MASTER_OF_THE_SWORD : MASTER_OF_THE_SWORD_SUN, chr.getSkillLevel(MASTER_OF_THE_SWORD)};
        }
        if (chr.hasSkill(EQUINOX_MASTERY)) {
            return new int[]{moon ? EQUINOX_MASTERY : EQUINOX_MASTERY_SUN, chr.getSkillLevel(EQUINOX_MASTERY)};
        }
        if (chr.hasSkill(EQUINOX_CYCLE)) {
            return new int[]{moon ? EQUINOX_CYCLE_MOON : EQUINOX_CYCLE_SUN, chr.getSkillLevel(EQUINOX_CYCLE)};
        }
        // Falling Moon follows Rising Sun's level
        return new int[]{moon ? FALLING_MOON : RISING_SUN, Math.max(1, chr.getSkillLevel(RISING_SUN))};
    }

    private void putMoonStats(int reason) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int[] stance = getStanceSkill(true);
        SkillInfo si = SkillData.getSkillInfoById(stance[0]);
        if (si == null) {
            return;
        }
        Option o1 = new Option();
        o1.nOption = si.getValue(indieCr, stance[1]);
        o1.rOption = reason;
        tsm.putCharacterStatValue(IndieCr, o1);
        Option o2 = new Option();
        o2.nOption = MOON_BUCKSHOT;
        o2.rOption = reason;
        tsm.putCharacterStatValue(BuckShot, o2);
    }

    private void putSunStats(int reason) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        int[] stance = getStanceSkill(false);
        SkillInfo si = SkillData.getSkillInfoById(stance[0]);
        if (si == null) {
            return;
        }
        Option o1 = new Option();
        o1.nOption = si.getValue(indiePMdR, stance[1]);
        o1.rOption = reason;
        tsm.putCharacterStatValue(IndiePMdR, o1);
        Option o2 = new Option();
        o2.nOption = si.getValue(indieBooster, stance[1]);
        o2.rOption = reason;
        tsm.putCharacterStatValue(IndieBooster, o2);
    }

    /**
     * Switches to Falling Moon (moon) or Rising Sun. With Master of the Sword during Equinox Cycle the other stance's
     * stats stay on as hidden buffs.
     */
    private void applyStance(boolean moon) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        boolean both = tsm.hasStat(GlimmeringTime) && chr.hasSkill(MASTER_OF_THE_SWORD);
        tsm.removeStatsBySkill(moon ? RISING_SUN : FALLING_MOON);
        tsm.removeStatsBySkill(moon ? EQUINOX_CYCLE_MOON : EQUINOX_CYCLE_SUN);
        Option o1 = new Option();
        o1.nOption = moon ? 1 : 2;
        o1.rOption = moon ? FALLING_MOON : RISING_SUN;
        o1.bOption = moon ? 0 : 1;
        tsm.putCharacterStatValue(PoseType, o1);
        if (moon) {
            putMoonStats(FALLING_MOON);
            if (both) {
                putSunStats(EQUINOX_CYCLE_SUN);
            } else {
                tsm.removeStatsBySkill(EQUINOX_CYCLE_SUN);
            }
        } else {
            putSunStats(RISING_SUN);
            if (both) {
                putMoonStats(EQUINOX_CYCLE_MOON);
            } else {
                tsm.removeStatsBySkill(EQUINOX_CYCLE_MOON);
            }
        }
        tsm.sendSetStatPacket();
    }

    // Cosmic Orbs -----------------------------------------------------------------------------------------------------

    private int getCosmicOrbs() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        return tsm.hasStat(CosmicOrb) ? tsm.getOption(CosmicOrb).nOption : 0;
    }

    private int getCosmicForgeOrbs() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        return tsm.hasStat(LightOfSpirit) ? tsm.getOption(LightOfSpirit).nOption : 0;
    }

    private int getMaxCosmicOrbs() {
        SkillInfo si = SkillData.getSkillInfoById(SOUL_ELEMENT);
        int max = si.getValue(x, Math.max(1, chr.getSkillLevel(SOUL_ELEMENT)));
        for (int blessing : new int[]{SOUL_BLESSING, SOUL_BLESSING_II, SOUL_BLESSING_III}) {
            if (chr.hasSkill(blessing)) {
                max += SkillData.getSkillInfoById(blessing).getValue(x, chr.getSkillLevel(blessing));
            }
        }
        return max + getCosmicForgeOrbs();
    }

    private int getCosmicOrbProp() {
        SkillInfo si = SkillData.getSkillInfoById(SOUL_ELEMENT);
        int chance = si.getValue(prop, Math.max(1, chr.getSkillLevel(SOUL_ELEMENT)));
        for (int blessing : new int[]{SOUL_BLESSING, SOUL_BLESSING_II, SOUL_BLESSING_III}) {
            if (chr.hasSkill(blessing)) {
                chance += SkillData.getSkillInfoById(blessing).getValue(prop, chr.getSkillLevel(blessing));
            }
        }
        return chance;
    }

    private void addCosmicOrbs(int amount) {
        if (!chr.getTemporaryStatManager().hasStat(ElementSoul) || amount <= 0) {
            return;
        }
        setCosmicOrbs(Math.min(getMaxCosmicOrbs(), getCosmicOrbs() + amount), true);
    }

    /**
     * Takes all Cosmic Orbs.
     *
     * @return the number of orbs taken
     */
    private int consumeCosmicOrbs() {
        int count = getCosmicOrbs();
        if (count > 0) {
            setCosmicOrbs(0, false);
        }
        return count;
    }

    private void setCosmicOrbs(int count, boolean created) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (!tsm.hasStat(ElementSoul)) {
            return;
        }
        SkillInfo si = SkillData.getSkillInfoById(SOUL_ELEMENT);
        int slv = Math.max(1, chr.getSkillLevel(SOUL_ELEMENT));
        if (count <= 0) {
            tsm.removeStatsBySkill(SOUL_ELEMENT_ORB);
            tsm.sendResetStatPacket(); // or the client keeps drawing the orbs
        } else {
            Option o1 = new Option();
            o1.nOption = count;
            o1.rOption = SOUL_ELEMENT_ORB;
            o1.tOption = si.getValue(y, slv);
            tsm.putCharacterStatValue(CosmicOrb, o1, true);
        }
        if (created) {
            int pad = si.getValue(u2, slv);
            int pmdr = 0;
            for (int blessing : new int[]{SOUL_BLESSING, SOUL_BLESSING_II, SOUL_BLESSING_III}) {
                if (chr.hasSkill(blessing)) {
                    SkillInfo bsi = SkillData.getSkillInfoById(blessing);
                    pad += bsi.getValue(y, chr.getSkillLevel(blessing));
                    pmdr += bsi.getValue(z, chr.getSkillLevel(blessing));
                }
            }
            Option o2 = new Option();
            o2.nOption = pad;
            o2.rOption = SOUL_ELEMENT_BUFF;
            o2.tOption = si.getValue(u, slv);
            tsm.putCharacterStatValue(IndiePAD, o2);
            if (pmdr > 0) {
                Option o3 = new Option();
                o3.nOption = pmdr;
                o3.rOption = SOUL_ELEMENT_BUFF;
                o3.tOption = si.getValue(u, slv);
                tsm.putCharacterStatValue(IndiePMdR, o3);
            }
        }
        updateElementSoul(count);
    }

    private void updateElementSoul(int count) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        o.nOption = Math.max(1, chr.getSkillLevel(SOUL_ELEMENT));
        o.rOption = SOUL_ELEMENT;
        o.yOption = count;
        tsm.putCharacterStatValue(ElementSoul, o, true);
        tsm.sendSetStatPacket();
    }

    // Auto Mode: fires after a Solar/Luna hit whenever it is off cooldown (v271: no orbs to fill)
    private void cosmicBurstAuto() {
        if (!chr.hasSkill(COSMIC_BURST) || chr.hasSkillOnCooldown(COSMIC_BURST) || !isSkillLocked(COSMIC_BURST)) {
            return;
        }
        int burstId = chr.hasSkill(HEXA_COSMIC_BURST) ? HEXA_COSMIC_BURST : COSMIC_BURST;
        int slv = chr.getSkillLevel(COSMIC_BURST);
        chr.setSkillCooldown(COSMIC_BURST, slv); // hardcode-ok
        cosmicBurst(burstId);
        chr.write(UserPacket.effect(Effect.skillUse(burstId, chr.getLevel(), slv, 0)));
        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.skillUse(burstId, chr.getLevel(), slv, 0)), chr);
    }

    /**
     * v271 Cosmic Burst releases bulletCount orbs (Cosmic Forge: +x), no stored orbs needed; one Second Atom each,
     * bosses with the highest HP first, one enemy per orb in turn.
     */
    private void cosmicBurst(int burstId) {
        SkillInfo si = SkillData.getSkillInfoById(burstId);
        if (si == null || si.getSecondAtomInfos().isEmpty() || chr.getField() == null) {
            return;
        }
        int burstLv = Math.max(1, chr.getSkillLevel(burstId));
        int count = Math.max(1, si.getValue(bulletCount, burstLv)) + getCosmicForgeOrbs();
        consumeCosmicOrbs();
        Rect rect = si.getFirstRect() != null ? chr.getRectAround(si.getFirstRect())
                : chr.getRectAround(new Rect(-COSMIC_BURST_RANGE, -COSMIC_BURST_RANGE, COSMIC_BURST_RANGE,
                COSMIC_BURST_RANGE));
        if (si.getFirstRect() != null && !chr.isLeft()) {
            rect = rect.horizontalFlipAround(chr.getPosition().getX());
        }
        List<Mob> mobs = new ArrayList<>(chr.getField().getMobsInRect(rect));
        mobs.sort(Comparator.comparing((Mob m) -> !m.isBoss()).thenComparing(m -> -m.getHp()));
        List<SecondAtom> atoms = new ArrayList<>();
        List<SecondAtomInfo> infos = si.getSecondAtomInfos();
        for (int i = 0; i < count; i++) {
            SecondAtomInfo sai = infos.get(i % infos.size());
            Mob mob = mobs.isEmpty() ? null : mobs.get(i % mobs.size());
            SecondAtom atom = SecondAtom.createFromInfo(chr, burstId, sai);
            atom.setKey(i);
            atom.setTargetId(mob != null ? mob.getObjectId() : 0);
            Position pos = chr.getPosition().deepCopy();
            if (sai.getPosition() != null) {
                pos = pos.add(sai.getPosition());
            }
            atom.setPosition(pos);
            atoms.add(atom);
        }
        chr.spawnSecondAtoms(atoms);
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        Summon summon;
        Field field = chr.getField();
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        int count;
        switch (skillId) {
            case TRUE_SIGHT:
                Rect rectAround = chr.getPosition().getRectAround(si.getFirstRect() != null
                        ? si.getFirstRect() : new Rect(new Position(-500, -500), new Position(500, 500)));
                if (!chr.isLeft()) {
                    rectAround = rectAround.horizontalFlipAround(chr.getPosition().getX());
                }
                int sightTime = si.getValue(time, slv) + (chr.hasSkill(TRUE_SIGHT_PERSIST) ? 20 : 0);
                int guardBreak = chr.hasSkill(TRUE_SIGHT_GUARDBREAK)
                        ? SkillData.getSkillInfoById(TRUE_SIGHT_GUARDBREAK).getValue(w, 1) : 0;
                int enhance = chr.hasSkill(TRUE_SIGHT_ENHANCE)
                        ? SkillData.getSkillInfoById(TRUE_SIGHT_ENHANCE).getValue(y, 1) : 0;
                o1.nOption = -(si.getValue(v, slv) + guardBreak);
                o1.rOption = skillId;
                o1.tOption = sightTime;
                o2.nOption = si.getValue(y, slv) + enhance;
                o2.rOption = skillId;
                o2.tOption = sightTime;
                o3.nOption = 1;
                o3.rOption = skillId;
                o3.tOption = sightTime;
                int maxMobs = Math.max(1, si.getValue(mobCount, slv));
                count = 0;
                for (Mob mob : chr.getField().getMobsInRect(rectAround)) {
                    if (count >= maxMobs) {
                        break;
                    }
                    if (mob.getHp() <= 0 || !Util.succeedProp(si.getValue(prop, slv))) {
                        continue;
                    }
                    MobTemporaryStat mts = mob.getTemporaryStat();
                    mts.addStatOptionsAndBroadcast(chr, MobStat.PDR, o1);
                    mts.addStatOptionsAndBroadcast(chr, MobStat.MDR, o1);
                    mts.addStatOptionsAndBroadcast(chr, MobStat.FinalDmgReceived, o2);
                    mts.addStatOptionsAndBroadcast(chr, MobStat.TrueSight, o3);
                    count++;
                }
                break;
            case SOUL_ELEMENT:
                if (tsm.hasStat(ElementSoul)) {
                    tsm.removeStatsBySkill(SOUL_ELEMENT);
                    tsm.removeStatsBySkill(SOUL_ELEMENT_ORB);
                    tsm.removeStatsBySkill(SOUL_ELEMENT_BUFF);
                } else {
                    updateElementSoul(0);
                }
                break;
            case FALLING_MOON:
                applyStance(true);
                break;
            case RISING_SUN:
                applyStance(false);
                if (isSkillLocked(EQUINOX_CYCLE) && chr.hasSkill(EQUINOX_CYCLE) && !tsm.hasStat(GlimmeringTime)) {
                    o1.nOption = 1;
                    o1.rOption = EQUINOX_CYCLE;
                    tsm.putCharacterStatValue(GlimmeringTime, o1);
                }
                break;
            case EQUINOX_CYCLE:
                o1.nOption = 1;
                o1.rOption = skillId;
                tsm.putCharacterStatValue(GlimmeringTime, o1);
                break;
            case COSMIC_MATTER:
                consumeCosmicOrbs();
                break;
            case COSMIC_SHOWER:
            case HEXA_COSMIC_SHOWER:
                count = consumeCosmicOrbs();
                summon = Summon.getSummonByNoCTS(chr, skillId, slv);
                summon.setSummonTerm(si.getValue(time, slv) + count * si.getValue(u, slv)
                        + (chr.hasSkill(COSMIC_SHOWER_PERSIST) ? 10 : 0));
                summon.setMoveAbility(MoveAbility.Stop);
                summon.setAssistType(AssistType.Attack);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case COSMIC_BURST:
            case HEXA_COSMIC_BURST:
                cosmicBurst(skillId);
                break;
            case SOUL_FORGE:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(LightOfSpirit, o1, true);
                o2.nOption = si.getValue(indieDamR, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o2, true);
                if (tsm.hasStat(ElementSoul)) {
                    setCosmicOrbs(getMaxCosmicOrbs(), true);
                }
                break;
            case GLORY_OF_THE_GUARDIANS_DW:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
            case CELESTIAL_DANCE:
                summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setAvatarLook(chr.getAvatarData().getAvatarLook());
                summon.setMoveAbility(MoveAbility.Walk);
                summon.setAttackActive(true);
                field.spawnSummonAndRemoveOld(summon);
                break;
            case RIFT_OF_DAMNATION:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(RiftOfDamnation, o1);
                addCosmicOrbs(getMaxCosmicOrbs());
                break;
            case SOUL_ECLIPSE:
                if (field.getSummonByChrAndSkillId(chr, skillId) == null) {
                    summon = Summon.getSummonByNoCTS(chr, skillId, slv);
                    summon.setMoveAbility(MoveAbility.Walk);
                    field.spawnSummonAndRemoveOld(summon);
                    o1.nOption = 1;
                    o1.rOption = skillId;
                    o1.tOption = SUMMON_CAST_INVINCIBLE_SEC;
                    tsm.putCharacterStatValue(IndieNotDamaged, o1, true); // Invincibility
                }
                break;
            case COSMOS:
                // v271: no orb cost; the galaxy lasts time ms
                consumeCosmicOrbs();
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                o1.setInMillis(true);
                tsm.putCharacterStatValue(Cosmos, o1, true);
                break;
        }
        tsm.sendSetStatPacket();
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void stopTimers() {
        if (willOfSteelTimer != null) {
            willOfSteelTimer.cancel(true);
        }
        super.stopTimers();
    }

    @Override
    public void updateTimerSkill() {
        EventManager.stopTimer(willOfSteelTimer);
        startWillOfSteel(chr);
        super.updateTimerSkill();
    }
}
