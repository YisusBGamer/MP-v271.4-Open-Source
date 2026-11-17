# 101930 [Aran] 3rd Job Advancement (start): v265 job advancement by quest
if chr.getJob() == 2110:
    sm.jobAdvance(chr.getJob() + 1)
    sm.startQuest(parentID)
    sm.completeQuestNoRewards(parentID)
