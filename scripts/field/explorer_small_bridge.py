# 130030006 (Empress's Road : Small Bridge, see CustomFieldScripts) - Explorer story: Phantom answers the bigger sign
# (A Bigger Phantom Signal 32340 - The Reason You Fight 32342)
if sm.hasQuest(32340) or sm.hasQuest(32341) or sm.hasQuest(32342) \
        or sm.hasQuestCompleted(32340) and not sm.hasQuestCompleted(32342):
    sm.spawnNpcIfAbsent(1520046, 1750, 88)
