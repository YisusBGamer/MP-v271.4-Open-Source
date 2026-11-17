# 101934 [Shade] 3rd Job Advancement (start): v265 job advancement by quest
if chr.getJob() == 2510:
    sm.jobAdvance(chr.getJob() + 1)
    sm.startQuest(parentID)
    sm.completeQuestNoRewards(parentID)
