# 39200 [Haven] A New Home (Haven 310070000). The Haven story keeps its steps on record 39200: <n>q opens step n,
# <n>m is a step's task (Quest.wz Check).
# One-Eye welcomes you; it opens the first step.
if sm.questStartDialogue(parentID) or sm.hasQuest(parentID):
    sm.completeQuestNoCheck(parentID)
    sm.setQRValue(39200, "0q", "1")
sm.dispose()
