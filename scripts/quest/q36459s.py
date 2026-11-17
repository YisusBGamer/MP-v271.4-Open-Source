# The Heart of a Knight (36459): no NPC to hand it in to; the final test takes place in the Cygnus Garden.
if sm.questStartDialogue(parentID, 1541104) and not sm.hasQuestCompleted(parentID):
    sm.completeQuestNoCheck(parentID)
if sm.hasQuestCompleted(parentID):
    sm.warp(993166407, 0)
sm.dispose()
