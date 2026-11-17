# [Moonbridge] Monsters in a Barrel (35614)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060040 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644632, -1100, 425, False)
            sm.spawnMob(8644633, -950, 425, False)
            sm.spawnMob(8644632, -800, 425, False)
            sm.spawnMob(8644633, -650, 425, False)
            sm.spawnMob(8644632, -450, 425, False)
            sm.spawnMob(8644633, -250, 425, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060040, 0)
    else:
        sm.warpInstanceIn(993060040, 0)
sm.dispose()
