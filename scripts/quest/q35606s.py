# [Moonbridge] Road to the Base (35606)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060013 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644632, -2550, 88, False)
            sm.spawnMob(8644633, -2350, 88, False)
            sm.spawnMob(8644632, -2100, 88, False)
            sm.spawnMob(8644633, -1850, 88, False)
            sm.spawnMob(8644632, -1600, 88, False)
            sm.spawnMob(8644633, -1300, 88, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060013, 0)
    else:
        sm.warpInstanceIn(993060013, 0)
sm.dispose()
