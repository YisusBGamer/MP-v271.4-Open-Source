# 36523 Behind the Rumor (start): v265 job advancement by quest
if chr.getJob() in (1111, 1211, 1311, 1411, 1511):
    sm.jobAdvance(chr.getJob() + 1)
    sm.startQuest(parentID)
    sm.completeQuestNoRewards(parentID)
