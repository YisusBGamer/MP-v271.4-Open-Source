# Chrysanthemum-Blooming Autumn (410007023) -> Bamboo-Resting Winter (410007024). While [Shangri-La] Last Remaining Season (38398) still
# needs Kaling (8880852, spawns nowhere in the data), this portal starts the fight in a private copy of
# Winter (Quest.wz: "go to Autumn and use the portal on the right to start defeating").
QUEST = 38398
PERIL = 8880852
TARGET = 410007024

if sm.getFieldID() == 410007023:
    if sm.hasQuest(QUEST) and not sm.isComplete(QUEST):
        sm.warpInstanceIn(TARGET, 1)
        sm.setInstanceTime(1800, TARGET)
        if not sm.hasMobsInField(TARGET, PERIL):
            sm.spawnMob(PERIL, 500, 64, False)
        sm.chatScript("Kaling appears! Defeat it.")
    else:
        sm.warp(TARGET, 1)
sm.dispose()
