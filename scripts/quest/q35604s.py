# [Moonbridge] Brink of Apocalypse (35604)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060010 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644632, -3900, 88, False)
            sm.spawnMob(8644632, -3600, 88, False)
            sm.spawnMob(8644633, -3300, 88, False)
            sm.spawnMob(8644632, -2700, 88, False)
            sm.spawnMob(8644633, -2400, 88, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060010, 0)
    else:
        sm.warpInstanceIn(993060010, 0)
sm.dispose()
