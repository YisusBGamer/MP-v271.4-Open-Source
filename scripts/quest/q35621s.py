# [Moonbridge] Terror in the Fog (35621)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060051 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644630, -700, 300, False)
            sm.spawnMob(8644630, -400, 300, False)
            sm.spawnMob(8644630, -100, 300, False)
            sm.spawnMob(8644630, 200, 300, False)
            sm.spawnMob(8644630, 500, 300, False)
            sm.spawnMob(8644630, 800, 300, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060051, 0)
    else:
        sm.warpInstanceIn(993060051, 0)
sm.dispose()
