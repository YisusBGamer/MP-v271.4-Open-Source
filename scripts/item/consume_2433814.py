# Mastery Book 30 (25%): raises the master level of one 4th job skill from 20 to 30
# (the skill must be at level 15 or higher)
from net.swordie.ms.loaders import SkillData
from net.swordie.ms.connection.packet import WvsContext
from net.swordie.ms.util import Util

MASTER_LEVEL = 30
SKILL_LEVEL_REQ = 15
CHANCE = 25

sm.setSpeakerID(9010000)
skills = []
for skill in chr.getSkills():
    si = SkillData.getSkillInfoById(skill.getSkillId())
    if si is not None and skill.getMasterLevel() == MASTER_LEVEL - 10 and si.getMaxLevel() >= MASTER_LEVEL:
        skills.append(skill)
if not skills:
    sm.sendSayOkay("You have no skill this Mastery Book can be used on.")
else:
    text = "Which skill should the Mastery Book raise to Master Level %d?\r\n" % MASTER_LEVEL
    for i, skill in enumerate(skills):
        text += "#L%d##s%d# #q%d# (Lv. %d, Master Level %d)#l\r\n" % (i, skill.getSkillId(), skill.getSkillId(),
                skill.getCurrentLevel(), skill.getMasterLevel())
    selection = sm.sendNext(text)
    if 0 <= selection < len(skills):
        skill = skills[selection]
        if skill.getCurrentLevel() < SKILL_LEVEL_REQ:
            sm.sendSayOkay("That skill has to be at level %d first (it's level %d)." % (SKILL_LEVEL_REQ, skill.getCurrentLevel()))
        else:
            sm.consumeItem(parentID)
            if CHANCE >= 100 or Util.succeedProp(CHANCE):
                skill.setMasterLevel(MASTER_LEVEL)
                chr.addSkill(skill)
                chr.write(WvsContext.changeSkillRecordResult(skill))
                sm.sendSayOkay("#s%d# #q%d#'s Master Level is now %d." % (skill.getSkillId(), skill.getSkillId(), MASTER_LEVEL))
            else:
                sm.sendSayOkay("The Mastery Book was used, but it didn't work.")
