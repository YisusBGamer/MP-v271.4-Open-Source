# A Knight's Knowledge (36455): no NPC to hand it in to; finishing it sends you to Knights Test Site 2.
if sm.questStartDialogue(parentID, 1541105) and not sm.hasQuestCompleted(parentID):
    sm.completeQuestNoCheck(parentID)
if sm.hasQuestCompleted(parentID):
    sm.warp(993166406, 0)
sm.dispose()
