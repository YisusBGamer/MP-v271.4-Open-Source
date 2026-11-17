# [Moonbridge] Destroy the Core (35631)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060081 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644612, 200, 156, False, 1000000000)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060081, 0)
    else:
        sm.warpInstanceIn(993060081, 0)
sm.dispose()
