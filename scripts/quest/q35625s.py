# [Moonbridge] Closer Inspection (35625)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060079 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644636, -600, -40, False)
            sm.spawnMob(8644637, -300, -40, False)
            sm.spawnMob(8644636, 0, -40, False)
            sm.spawnMob(8644637, 300, -40, False)
            sm.spawnMob(8644636, 600, -40, False)
            sm.spawnMob(8644637, 900, -40, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060079, 0)
    else:
        sm.warpInstanceIn(993060079, 0)
sm.dispose()
