package net.swordie.ms.client.jobs.adventurer.archer;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.info.HitInfo;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.adventurer.Beginner;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.connection.packet.Effect;
import net.swordie.ms.connection.packet.UserLocal;
import net.swordie.ms.connection.packet.UserPacket;
import net.swordie.ms.connection.packet.UserRemote;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.enums.MoveAbility;
import net.swordie.ms.enums.Stat;
import net.swordie.ms.life.Life;
import net.swordie.ms.life.Summon;
import net.swordie.ms.life.mob.Mob;
import net.swordie.ms.loaders.SkillData;
import net.swordie.ms.util.Util;
import net.swordie.ms.world.field.Field;

import static net.swordie.ms.client.character.skills.SkillStat.*;
import static net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat.*;

/**
 * Created on 12/14/2017.
 */
public class Archer extends Beginner {

    // V Skills
    public static final int FURY_OF_THE_WILD = 400001012;

    /** Phoenix, Frostprey, Shadow Raven and their HEXA forms: Fury of the Wild needs one out */
    public static boolean isFuryBird(int skillId) {
        return skillId == BowMaster.PHOENIX || skillId == BowMaster.HEXA_PHOENIX
                || skillId == Marksman.FREEZER || skillId == Marksman.HEXA_FREEZER
                || skillId == Pathfinder.RAVEN || skillId == Pathfinder.HEXA_RAVEN;
    }

    public Archer(Char chr) {
        super(chr);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        return id == JobConstants.JobEnum.BOWMAN.getJobId();
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
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case BowMaster.HOOKSHOT_BM:
            case Marksman.HOOKSHOT_MM:
                if (attackInfo.mobAttackInfo.size() > 0) {
                    int mobId = attackInfo.mobAttackInfo.get(0).mobId;
                    Life mob = chr.getField().getLifeByObjectID(mobId);
                    if (mob instanceof Mob) {
                        chr.write(UserPacket.effect(Effect.showHookEffect(skillID, chr.getLevel(), slv, 0,
                                attackInfo.left, mobId, mob.getX(), mob.getY())));
                        chr.getField().broadcastPacket(UserRemote.effect(chr.getId(), Effect.showHookEffect(skillID, chr.getLevel(), slv,
                                0, attackInfo.left, mobId, mob.getX(), mob.getY())));
                    }
                }
                break;
        }

        if (!JobConstants.isPhantom(chr.getJob())) {
            super.handleAttack(c, attackInfo);
        }
    }

    @Override
    public int getFinalAttackSkill() {
        Skill faSkill = getFinalAtkSkill();
        if (faSkill != null) {
            SkillInfo si = SkillData.getSkillInfoById(faSkill.getSkillId());
            int slv = faSkill.getCurrentLevel();
            int proc = si.getValue(prop, slv);
            if (Util.succeedProp(proc)) {
                return faSkill.getSkillId();
            }
        }
        return super.getFinalAttackSkill();
    }

    private Skill getFinalAtkSkill() {
        Skill skill = null;
        if (chr.hasSkill(BowMaster.FINAL_ATTACK_BOW)) {
            skill = chr.getSkill(BowMaster.FINAL_ATTACK_BOW);
        }
        if (chr.hasSkill(Marksman.FINAL_ATTACK_XBOW)) {
            skill = chr.getSkill(Marksman.FINAL_ATTACK_XBOW);
        }
        if (chr.hasSkill(BowMaster.ADVANCED_FINAL_ATTACK_BOW)) {
            skill = chr.getSkill(BowMaster.ADVANCED_FINAL_ATTACK_BOW);
        }
        return skill;
    }


    // Skill related methods -------------------------------------------------------------------------------------------

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        if (!JobConstants.isPhantom(chr.getJob())) {
            super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        }
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillId);
        }
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (skillId) {
            case BowMaster.SOUL_ARROW_BOW:
            case Marksman.SOUL_ARROW_XBOW:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(SoulArrow, o1);
                o2.nOption = si.getValue(epad, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(EPAD, o2);
                break;
            case BowMaster.BOW_BOOSTER:
            case Marksman.XBOW_BOOSTER:
            case Pathfinder.ANCIENT_BOW_BOOSTER:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieBooster, o1);
                break;
            case FURY_OF_THE_WILD:
                Summon oldSummon = chr.getField().getSummons()
                        .stream()
                        .filter(l -> isFuryBird(l.getSkillID()) && l.getChr() == chr)
                        .findAny().orElse(null);
                if (oldSummon == null) {
                    return;
                }
                chr.getField().removeLife(oldSummon.getObjectId(), true);
                tsm.removeStatsBySkill(BowMaster.PHOENIX);
                tsm.removeStatsBySkill(Marksman.FREEZER);
                tsm.removeStatsBySkill(Pathfinder.RAVEN);
                tsm.removeStatsBySkill(BowMaster.HEXA_PHOENIX);
                tsm.removeStatsBySkill(Marksman.HEXA_FREEZER);
                tsm.removeStatsBySkill(Pathfinder.HEXA_RAVEN);
                // Fall Through intended
            case BowMaster.PHOENIX:
            case Marksman.FREEZER:
            case Pathfinder.RAVEN:
                Summon summon = Summon.getSummonBy(chr, skillId, slv);
                summon.setFlyMob(true);
                // v271 Phoenix / Freezer have "move" but no "fly" action; Raven keeps "fly"
                summon.setMoveAbility(skillId == Pathfinder.RAVEN || skillId == Pathfinder.HEXA_RAVEN
                        ? MoveAbility.Fly : MoveAbility.Walk);
                Field field = chr.getField();
                field.spawnSummonAndRemoveOld(summon);
                break;
            case BowMaster.BLINK_SHOT:
            case Marksman.BLINK_SHOT:
                // the marker; using it again sends the teleport skill (id + 1), which the client moves for
                Summon marker = Summon.getSummonBy(chr, skillId, slv);
                marker.setFlyMob(false);
                marker.setMoveAbility(MoveAbility.Stop);
                chr.getField().spawnSummonAndRemoveOld(marker);
                break;
            case BowMaster.SHARP_EYES_BOW:
            case Marksman.SHARP_EYES_XBOW:
            case Pathfinder.SHARP_EYES_ABOW:
                // Short nOption is split in  2 bytes,  first one = CritDmg  second one = Crit%
                int cr = si.getValue(x, slv)
                        + chr.getSkillStatValue(x, BowMaster.SHARP_EYES_BOW_CRITICAL_CHANCE)
                        + chr.getSkillStatValue(x, Pathfinder.SHARP_EYES_ABOW_CRITICAL_CHANCE)
                        + chr.getSkillStatValue(x, Marksman.SHARP_EYES_XBOW_CRITICAL_CHANCE);
                int crDmg = si.getValue(y, slv);
                o1.xOption = cr;
                o1.yOption = crDmg;
                o1.nOption = (cr << 8) + crDmg;
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv)
                        + chr.getSkillStatValue(time, BowMaster.SHARP_EYES_BOW_PERSIST)
                        + chr.getSkillStatValue(time, Marksman.SHARP_EYES_XBOW_PERSIST)
                        + chr.getSkillStatValue(time, Pathfinder.SHARP_EYES_ABOW_PERSIST);
                //mOption is for the hyper passive
                if (chr.hasSkill(BowMaster.SHARP_EYES_BOW_GUARDBREAK) || chr.hasSkill(Marksman.SHARP_EYES_XBOW_GUARDBREAK) || chr.hasSkill(Pathfinder.SHARP_EYES_ABOW_GUARDBREAK)) {
                    o1.mOption =
                            chr.getSkillStatValue(ignoreMobpdpR, BowMaster.SHARP_EYES_BOW_GUARDBREAK)
                            + chr.getSkillStatValue(ignoreMobpdpR, Marksman.SHARP_EYES_XBOW_GUARDBREAK)
                            + chr.getSkillStatValue(ignoreMobpdpR, Pathfinder.SHARP_EYES_ABOW_GUARDBREAK); // IED
                }
                tsm.putCharacterStatValue(SharpEyes, o1);
                break;
            case BowMaster.ILLUSION_STEP_BOW:
            case Marksman.ILLUSION_STEP_XBOW:
                o1.nOption = si.getValue(indieDEX, slv);
                o1.rOption = skillId;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDEX, o1);
                o2.nOption = si.getValue(x, slv);
                o2.rOption = skillId;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(EVAR, o2);
                break;
            case Marksman.EPIC_ADVENTURE_XBOW:
            case BowMaster.EPIC_ADVENTURE_BOW:
            case Pathfinder.EPIC_ADVENTURE_ABOW:
                o1.rOption = skillId;
                o1.nOption = si.getValue(indieDamR, slv);
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                break;
        }
        tsm.sendSetStatPacket();
    }


    // Hit related methods ---------------------------------------------------------------------------------------------

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        if (hitInfo.hpDamage == 0 && hitInfo.mpDamage == 0) {
            chr.write(UserLocal.dodgeSkillReady());
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    @Override
    public void handleLevelUp() {
        super.handleLevelUp();
        if (!net.swordie.ms.client.character.JobPathModule.classHandlesAdvancement(chr)) {
            return; // job path chosen with @job, or a reborn character: JobPathModule advances it
        }
        // hacks to bypass the quest glitch (accept but no packet)
        short level = chr.getLevel();
        QuestManager qm = chr.getQuestManager();
        boolean isPathfinder = JobConstants.isPathFinder(chr.getJob());
        if (level == 30) {
            if (isPathfinder) {
                chr.advanceJob(JobConstants.JobEnum.PATHFINDER_2.getJobId()); // + its advancement SP
                chr.addSkill(Pathfinder.ANCIENT_CURSE, 2, 4);
            }
        } else if (level == 60) {
            if (isPathfinder) {
                chr.advanceJob(JobConstants.JobEnum.PATHFINDER_3.getJobId()); // + its advancement SP
                chr.addSkill(Pathfinder.ANCIENT_CURSE, 3, 4);
            }
        } else if (level == 100) {
            if (isPathfinder) {
                chr.advanceJob(JobConstants.JobEnum.PATHFINDER_4.getJobId()); // + its advancement SP
                chr.addSkill(Pathfinder.ANCIENT_CURSE, 4, 4);
            }
        }
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
    }

    @Override
    public void stopTimers() {
        super.stopTimers();
    }
}
