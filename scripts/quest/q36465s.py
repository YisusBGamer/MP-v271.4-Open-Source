# The Mock Battle (36465) at Neinheart: no NPC to hand it in to.
if sm.questStartDialogue(parentID, 1101002) and not sm.hasQuestCompleted(parentID):
    sm.completeQuestNoCheck(parentID)
sm.dispose()
