# [Moonbridge] Guiding Light (35629)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060069 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644636, -4300, -488, False)
            sm.spawnMob(8644637, -3900, -488, False)
            sm.spawnMob(8644636, -3500, -488, False)
            sm.spawnMob(8644637, -2500, -488, False)
            sm.spawnMob(8644636, -2100, -488, False)
            sm.spawnMob(8644637, -1700, -488, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060069, 0)
    else:
        sm.warpInstanceIn(993060069, 0)
sm.dispose()
