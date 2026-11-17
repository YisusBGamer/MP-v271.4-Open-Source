# 39204 [Haven] Thanks for the Save (Haven 310070000). The Haven story keeps its steps on record 39200: <n>q opens step n,
# <n>m is a step's task (Quest.wz Check).
# No NPC to hand it in to: done once its scene is played, and the next step opens.
if sm.questStartDialogue(parentID) or sm.hasQuest(parentID):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(39200, "4q", "1")
sm.dispose()
