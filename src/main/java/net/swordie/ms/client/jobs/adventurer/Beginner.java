package net.swordie.ms.client.jobs.adventurer;

import net.swordie.ms.client.Client;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.CharacterStat;
import net.swordie.ms.client.character.skills.Option;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.info.AttackInfo;
import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.character.skills.info.SkillUseInfo;
import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;
import net.swordie.ms.client.character.skills.temp.TemporaryStatManager;
import net.swordie.ms.client.jobs.Job;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.constants.JobConstants;
import net.swordie.ms.loaders.SkillData;

/**
 * Created on 12/14/2017.
 */
public class Beginner extends Job {
    public static final int THREE_SNAILS = 1000;
    public static final int RECOVERY = 1001;
    public static final int NIMBLE_FEET = 1002;

    public static final int MAPLE_RETURN = 1281;

    private static final int NIMBLE_FEET_SPEED = 20; // hardcode-ok: tooltip text says 10 for 4 sec, no Skill.wz entry
    private static final int NIMBLE_FEET_SEC = 12; // hardcode-ok
    public static final int SOMEWHERE_IN_COCO_ISLAND_FIELD = 3000600;
    public static final int MAPLE_TREE_HILL_FIELD = 4000011;
    public static final int SCENE_CHANGE_1_FIELD = 552000060;

    private static final int[] addedSkills = new int[]{
            RECOVERY,
            NIMBLE_FEET,
            THREE_SNAILS
    };

    public Beginner(Char chr) {
        super(chr);

        if (isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setRootId(0);
                    skill.setMasterLevel(3);
                    skill.setMaxLevel(3);
                    chr.addSkill(skill);
                }
            }
            if (!JobConstants.isPathFinder(chr.getJob()) && !JobConstants.isDualBlade(chr.getJob()) && !JobConstants.isJett(chr.getJob())) {
                Skill skill = SkillData.getSkillDeepCopyById(MAPLE_RETURN);
                skill.setCurrentLevel(skill.getMasterLevel());
                chr.addSkill(skill);
            }
        }
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        super.handleAttack(c, attackInfo);
    }

    @Override
    public void handleSkill(Char chr, TemporaryStatManager tsm, int skillId, int slv, InPacket inPacket, SkillUseInfo skillUseInfo) {
        super.handleSkill(chr, tsm, skillId, slv, inPacket, skillUseInfo);
        Skill skill = chr.getSkill(skillId);
        SkillInfo si = null;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skillId);
        }
        Option o1 = new Option();
        switch (skillId) {
            case NIMBLE_FEET:
                o1.nOption = NIMBLE_FEET_SPEED;
                o1.rOption = NIMBLE_FEET;
                o1.tOption = NIMBLE_FEET_SEC;
                tsm.putCharacterStatValue(CharacterTemporaryStat.IndieSpeed, o1);
                break;
            case RECOVERY:

                break;
        }
        tsm.sendSetStatPacket();
    }


    @Override
    public boolean isHandlerOfJob(short id) {
        JobConstants.JobEnum job = JobConstants.JobEnum.getJobById(id);
        return job == JobConstants.JobEnum.BEGINNER;
    }

    @Override
    public void setCharCreationStats(Char chr) {
        super.setCharCreationStats(chr);
        CharacterStat cs = chr.getAvatarData().getCharacterStat();
        if (chr.getSubJob() == 1) {
            // v265: Dual Blades start on Maple Island like other Explorers (36301-36307), then Thief of the Shade (2604);
            // the old Secret Garden intro quests (2350+, 2600-2603) are blocked
            cs.setPosMap(MAPLE_TREE_HILL_FIELD);
        } else if (chr.getSubJob() == 2) {
            cs.setPosMap(SOMEWHERE_IN_COCO_ISLAND_FIELD);
        } else if (chr.getSubJob() == 10){ //Jett
            cs.setPosMap(SCENE_CHANGE_1_FIELD);
        }else {
            cs.setPosMap(MAPLE_TREE_HILL_FIELD);
        }
    }
}
