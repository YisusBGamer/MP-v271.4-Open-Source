# Orchid Leaf-Green Summer (410007022) -> Chrysanthemum-Blooming Autumn (410007023). While [Shangri-La] Collapsing Shangri-La 3 (38397) still
# needs Hundun (8880851, spawns nowhere in the data), this portal starts the fight in a private copy of
# Autumn (Quest.wz: "go to Summer and use the portal on the right to start defeating").
QUEST = 38397
PERIL = 8880851
TARGET = 410007023

if sm.getFieldID() == 410007022:
    if sm.hasQuest(QUEST) and not sm.isComplete(QUEST):
        sm.warpInstanceIn(TARGET, 1)
        sm.setInstanceTime(1800, TARGET)
        if not sm.hasMobsInField(TARGET, PERIL):
            sm.spawnMob(PERIL, 300, 64, False)
        sm.chatScript("Hundun appears! Defeat it.")
    else:
        sm.warp(TARGET, 1)
sm.dispose()
