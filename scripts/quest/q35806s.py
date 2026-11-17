# [Limina] Guard Captain Darknell (35806)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993070076 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8645039, 300, 29, False, 2100000000)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993070076, 0)
    else:
        sm.warpInstanceIn(993070076, 0)
sm.dispose()
