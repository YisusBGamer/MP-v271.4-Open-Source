# Intruder Search (36461) at Neinheart: no NPC to hand it in to. Who Is Baroq? Part 1 (36462) follows on its own;
# the search starts in Ereve's Training Forest, with Kiku.
if sm.questStartDialogue(parentID, 1541106) and not sm.hasQuestCompleted(parentID):
    sm.completeQuestNoCheck(parentID)
if sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(36462) and not sm.hasQuestCompleted(36462):
        sm.startQuestNoCheck(36462)
    sm.warp(130010000, 0)
sm.dispose()
