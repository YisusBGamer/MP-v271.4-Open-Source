# 101931 [Aran] 4th Job Advancement (start): v265 job advancement by quest
if chr.getJob() == 2111:
    sm.jobAdvance(chr.getJob() + 1)
    sm.startQuest(parentID)
    sm.completeQuestNoRewards(parentID)
