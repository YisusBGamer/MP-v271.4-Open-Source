# 39206 [Haven] Inside Black Heaven Again (Haven 310070000). The Haven story keeps its steps on record 39200: <n>q opens step n,
# <n>m is a step's task (Quest.wz Check).
# Its task (6m) names no map in the data: the trip back inside is this scene, so it is set here.
if sm.questStartDialogue(parentID) or sm.hasQuest(parentID):
    sm.setQRValue(39200, "6m", "1")
sm.dispose()
