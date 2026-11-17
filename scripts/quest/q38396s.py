# 38396 [Shangri-La] Collapsing Shangri-La 2 - start (the scroll in Orchid Leaf-Green Summer 410007022). Examining
# the scroll brings out Taowu (8880850), one of the Four Perils, which spawns nowhere in the data: the fight is in a
# private copy of the map (Quest.wz: leaving it without beating Taowu means forfeiting the quest).
TAOWU = 8880850
SUMMER = 410007022

if sm.questStartDialogue(parentID, 3006183):
    sm.warpInstanceIn(SUMMER, 0)
    sm.setInstanceTime(1800, SUMMER)
    sm.spawnMob(TAOWU, 200, 64, False)
    sm.chatScript("Taowu, one of the Four Perils, appears! Defeat it.")
sm.dispose()
