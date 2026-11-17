# [Moonbridge] Looming Despair (35628)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060067 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644636, -4200, -488, False)
            sm.spawnMob(8644637, -3800, -488, False)
            sm.spawnMob(8644608, -3400, -488, False)
            sm.spawnMob(8644636, -2600, -488, False)
            sm.spawnMob(8644637, -2200, -488, False)
            sm.spawnMob(8644608, -1800, -488, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060067, 0)
    else:
        sm.warpInstanceIn(993060067, 0)
sm.dispose()
