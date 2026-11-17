# [Moonbridge] Complete the Esfera Investigation (35602)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060003 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644600, -900, -415, False)
            sm.spawnMob(8644601, -500, -415, False)
            sm.spawnMob(8644602, 0, -295, False)
            sm.spawnMob(8644603, 500, -415, False)
            sm.spawnMob(8644604, 900, -415, False)
            sm.spawnMob(8644600, 300, -295, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060003, 0)
    else:
        sm.warpInstanceIn(993060003, 0)
sm.dispose()
