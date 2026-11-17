# 101661 [Angelic Buster] 3rd Job Advancement (start): v265 job advancement by quest
if chr.getJob() == 6510:
    sm.jobAdvance(chr.getJob() + 1)
    sm.startQuest(parentID)
    sm.completeQuestNoRewards(parentID)
