# 101933 [Shade] 2nd Job Advancement (start): v265 job advancement by quest
if chr.getJob() == 2500 and chr.getLevel() >= 30:
    sm.startQuest(parentID)
    sm.jobAdvance(2510)
    sm.completeQuestNoRewards(parentID)
sm.dispose()
