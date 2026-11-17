package net.swordie.ms.client.jobs.adventurer.thief;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.debuff.DebuffInfo;
import net.swordie.ms.client.character.skills.debuff.DebuffUtil;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.WvsContext;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.constants.SkillConstants;
import net.swordie.ms.enums.AssistType;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.MobStat;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import java.util.Map;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

public class BladeMaster extends Thief {
    public static final int RETURN = 1282;


    public static final int SELF_HASTE = 4301003; //Buff


    public static final int KATARA_BOOSTER = 4311009; //Buff


    public static final int VENOM_DB = 4320005;
    public static final int FLYING_ASSAULTER = 4321006; //Special Attack (Stun Debuff)
    public static final int FLASHBANG = 4321002; //Special Attack
    public static final int UPPER_STAB = 4321004;


    public static final int CHAINS_OF_HELL = 4331006; //Special Attack (Stun Debuff)
    public static final int MIRROR_IMAGE = 4331002; //Buff
    public static final int SHADOW_MELD = 4330009;
    public static final int LIFE_DRAIN = 4330007;
    public static final int ADVANCED_DARK_SIGHT_DB = 4330001;


    public static final int PHANTOM_BLOW = 4341009;
    public static final int BLADE_FURY = 4341004;
    public static final int FINAL_CUT = 4341002; //Special Attack
    public static final int SUDDEN_RAID_DB = 4341011; //Special Attack
    public static final int MAPLE_WARRIOR_DB = 4341000; //Buff
    public static final int TOXIC_VENOM_DB = 4340012;
    public static final int HEROS_WILL_DB = 4341008;
    public static final int EPIC_ADVENTURE_DB = 4341053;
    public static final int ASURAS_ANGER = 4341052;
    private static final int ASURA_DAM_REDUCE_R = 50; // data s5
    private static final int UPPER_STAB_SEC = 3; // hardcode-ok: Upper Stab not in v271 Skill.wz
    private static final int CHAINS_OF_HELL_INVINCIBLE_SEC = 3; // hardcode-ok: data t=1.5 plus "invincible during attack"
    public static final int BLADE_CLONE = 4341054;
    public static final int HEXA_BLADE_CLONE = 4361002;
    public static final int KARMA_BLADE = 4361500;
    public static final int KARMA_BLADE_BUFF = 4361501;
    public static final int KARMA_BLADE_EXTRA = 4361502;
    public static final int KARMA_BLADE_AFTER_ATTACK = 4361503;
    public static final int SLASH_STORM_BOOST = 4321007;
    public static final int BLOODY_STORM = 4331000;
    public static final int BLADE_ASCENSION = 4331011;
    public static final int MORTALITY = 4360006;
    public static final int HEXA_PHANTOM_BLOW = 4361000;
    public static final int HEXA_ASURAS_ANGER = 4361001;
    public static final int HEXA_BLADE_FURY = 4361003;
    public static final int FURY_JET = 4361004;
    public static final int HEXA_SUDDEN_RAID = 4361005;
    // Skill.wz 4361501/skillList: the attacks that feed Karma Blade
    private static final java.util.Set<Integer> KARMA_BLADE_SKILLS = java.util.Set.of(4001013, 4301004, 4311002, 4311003,
            FLYING_ASSAULTER, FLASHBANG, SLASH_STORM_BOOST, BLOODY_STORM, BLADE_ASCENSION, CHAINS_OF_HELL, FINAL_CUT, BLADE_FURY, PHANTOM_BLOW, SUDDEN_RAID_DB, ASURAS_ANGER, BladeMaster.BLADE_TEMPEST,
            BladeMaster.BLADES_OF_DESTINY, BladeMaster.BLADE_TORNADO, BladeMaster.BLADE_TORNADO_SHOOT_OBJECT, BladeMaster.HAUNTED_EDGE, BladeMaster.HAUNTED_EDGE_ASURA, BladeMaster.HAUNTED_EDGE_ASURA_WIND, BladeMaster.HAUNTED_EDGE_YAKSA, HEXA_PHANTOM_BLOW, HEXA_ASURAS_ANGER, HEXA_BLADE_FURY,
            FURY_JET, HEXA_SUDDEN_RAID, MORTALITY);
    public static final int MIRRORED_TARGET = 4341006;


    // V skills
    public static final int BLADE_TEMPEST = 400041006;
    public static final int BLADES_OF_DESTINY = 400041021;
    public static final int BLADE_TORNADO = 400041042;
    public static final int BLADE_TORNADO_SHOOT_OBJECT = 400041043;
    public static final int HAUNTED_EDGE = 400041075;
    public static final int HAUNTED_EDGE_ASURA = 400041076;
    public static final int HAUNTED_EDGE_ASURA_WIND = 400041077;
    public static final int HAUNTED_EDGE_YAKSA = 400041078;


    public BladeMaster(Char chr) {
        super(chr);
        if (isHandlerOfJob(chr.getJob())) {
            if (!chr.hasSkill(RETURN)) {
                Skill skill = SkillData.getSkillDeepCopyById(RETURN);
                skill.setCurrentLevel(skill.getMasterLevel());
                chr.addSkill(skill);
            }
        }
    }


    @Override
    public boolean isHandlerOfJob(short id) {
        return JobConstants.isDualBlade(id);
    }

    
    // Attack related methods ------------------------------------------------------------------------------------------

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        int slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }
        if (!SkillConstants.isSummon(attackInfo.skillId) && hasHitMobs) {

            // Life Drain
            recoverHPByLifeDrain();
            procKarmaBlade(tsm, attackInfo.skillId);
        }
        if (attackInfo.skillId == KARMA_BLADE_AFTER_ATTACK && !tsm.hasStat(KarmaBlade)) {
            SkillInfo kbSi = SkillData.getSkillInfoById(KARMA_BLADE_BUFF);
            int kbSlv = chr.getSkillLevel(KARMA_BLADE);
            if (kbSi != null && kbSlv > 0) {
                tsm.putCharacterStatValue(KarmaBlade, new Option(kbSi.getValue(x, kbSlv), KARMA_BLADE_BUFF, kbSi.getValue(time, kbSlv)));
                tsm.sendSetStatPacket();
            }
        }

        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        switch (attackInfo.skillId) {
            case FLASHBANG:
                o1.nOption = -si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                o2.nOption = si.getValue(z, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);

                DebuffUtil.applyDebuffOnMobs(chr, Map.of(MobStat.ACC, o1, MobStat.AddDamSkill, o2), attackInfo.getMobIds(), new DebuffInfo(si.getValue(prop, slv)));
                break;
            case UPPER_STAB:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = UPPER_STAB_SEC;

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Lifting, o1, attackInfo.getMobIds(), new DebuffInfo(true, si.getValue(prop, slv)));
                break;
            case FLYING_ASSAULTER:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Stun, o1, attackInfo.getMobIds(), new DebuffInfo(true, si.getValue(prop, slv)));
                break;
            case CHAINS_OF_HELL:
                o1.nOption = 1;
                o1.rOption = skill.getSkillId();
                o1.tOption = si.getValue(time, slv);

                DebuffUtil.applyDebuffOnMobs(chr, MobStat.Stun, o1, attackInfo.getMobIds(), new DebuffInfo(true, si.getValue(prop, slv)));
                break;
            case FINAL_CUT:
                o1.nOption = 100 + si.getValue(w, slv);
                o1.rOption = attackInfo.skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(FinalCut, o1);
                tsm.sendSetStatPacket();
                o2.nOption = 1;
                o2.rOption = attackInfo.skillId;
                o2.tOption = si.getValue(v, slv);
                tsm.putCharacterStatValue(IndieNotDamaged, o2, true);
                tsm.sendSetStatPacket();
                break;
        }
        super.handleAttack(c, attackInfo);
    }

    // Karma Blade's x charges: each listed attack spends one for a bonus hit, the last one sets off the extra strike
    private void procKarmaBlade(TemporaryStatManager tsm, int skillId) {
        if (!tsm.hasStat(KarmaBlade) || !KARMA_BLADE_SKILLS.contains(skillId)) {
            return;
        }
        Option o = tsm.getOption(KarmaBlade);
        int left = Math.max(o.nOption - 1, 0);
        if (left == 0) {
            tsm.removeStatsBySkill(KARMA_BLADE_BUFF);
            tsm.sendResetStatPacket();
            chr.write(net.swordie.ms.connection.packet.field.FieldPacket.registerExtraSkill(KARMA_BLADE_EXTRA,
                    new net.swordie.ms.client.character.skills.ExtraSkillInfo(KARMA_BLADE_EXTRA, chr.getPosition(),
                            chr.isLeft(), 0, 1)));
        } else {
            o.nOption = left;
            tsm.updateBuff(KarmaBlade, o);
            chr.write(net.swordie.ms.connection.packet.UserLocal.userBonusAttackRequest(KARMA_BLADE_BUFF));
        }
    }

    private void recoverHPByLifeDrain() {
        if (chr.hasSkill(LIFE_DRAIN)) {
            Skill skill = chr.getSkill(LIFE_DRAIN);
            int slv = skill.getCurrentLevel();
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            int proc = si.getValue(prop, slv);
            int amounthealed = si.getValue(x, slv);
            if (Util.succeedProp(proc)) {
                int healamount = (int) ((chr.getMaxHP()) / ((double) 100 / amounthealed));
                chr.heal(healamount);
            }
        }
    }

    @Override
    public int getFinalAttackSkill() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (tsm.getOptByCTSAndSkill(WindBreakerFinal, BLADE_CLONE) != null) {
            return chr.hasSkill(HEXA_BLADE_CLONE) ? HEXA_BLADE_CLONE : BLADE_CLONE;
        }
        return super.getFinalAttackSkill();
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
            case MIRRORED_TARGET:
                if (tsm.getOptByCTSAndSkill(ShadowPartner, MIRROR_IMAGE) != null) {
                    summon = Summon.getSummonBy(chr, skillId, slv);
                    summon.setFlyMob(false);
                    summon.setMoveAction((byte) 0);
                    summon.setMoveAbility(MoveAbility.Stop);
                    summon.setAssistType(AssistType.None);
                    summon.setAttackActive(false);
                    summon.setAvatarLook(chr.getAvatarData().getAvatarLook());
                    summon.setMaxHP(si.getValue(x, slv));
                    summon.setHp(summon.getMaxHP());
                    field.spawnSummonAndRemoveOld(summon);

                    // tsm.removeStatsBySkill(MIRROR_IMAGE);
                }
                break;
            case CHAINS_OF_HELL:
                o2.nOption = 1;
                o2.rOption = skillId;
                o2.tOption = CHAINS_OF_HELL_INVINCIBLE_SEC;
                tsm.putCharacterStatValue(IndieNotDamaged, o2, true);
                break;
            case ASURAS_ANGER: {
                // GMS: Asura n = x, and for the same time IndieBuffIcon 1, IndieDamReduceR s5, IndieFloating 1,
                // IndieAntiMagicShell 1 (s5 = 50: SkillStat has no s5)
                int dur = si.getValue(time, slv) > 0 ? si.getValue(time, slv) : 3;
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = dur;
                tsm.putCharacterStatValue(Asura, o1);
                tsm.putCharacterStatValue(IndieBuffIcon, new Option(1, skillId, dur));
                tsm.putCharacterStatValue(IndieDamReduceR, new Option(ASURA_DAM_REDUCE_R, skillId, dur));
                tsm.putCharacterStatValue(IndieFloating, new Option(1, skillId, dur));
                tsm.putCharacterStatValue(IndieAntiMagicShell, new Option(1, skillId, dur));
                break;
            }
            case BLADE_CLONE:
                o1.nOption = 1;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(WindBreakerFinal, o1);
                o2.nOption = si.getValue(indieDamR, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o2);
                break;
            case BLADES_OF_DESTINY:
                chr.write(WvsContext.skillUseResult(true, skillId));
                break;
        }
        tsm.sendSetStatPacket();
    }

    @Override
    public void handleSkillPrepareStop(Char chr, int skillId, SkillUseInfo sui) {
        if (skillId == FINAL_CUT) {
            return;
        }
        super.handleSkillPrepareStop(chr, skillId, sui);
    }

    // Hit related methods ---------------------------------------------------------------------------------------------
}
