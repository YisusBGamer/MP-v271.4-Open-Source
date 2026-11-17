# 101932 [Shade] 1st Job Advancement (start): v265 job advancement by quest
if chr.getJob() == 2005 and chr.getLevel() >= 10:
    sm.startQuest(parentID)
    sm.jobAdvance(2500)
    sm.giveSkill(25001000)  # Swift Strike
    sm.giveSkill(25001204, 4)  # Vulpes Leap
    sm.completeQuestNoRewards(parentID)
sm.dispose()
