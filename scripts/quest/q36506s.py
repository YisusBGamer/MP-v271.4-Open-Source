# 36506 The Advanced Knight's Trial (start): v265 job advancement by quest
if chr.getJob() in (1110, 1210, 1310, 1410, 1510):
    sm.jobAdvance(chr.getJob() + 1)
    sm.startQuest(parentID)
    sm.completeQuestNoRewards(parentID)
