# [Moonbridge] Foggy Depths (35615)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060049 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644634, -700, 300, False)
            sm.spawnMob(8644635, -400, 300, False)
            sm.spawnMob(8644634, -100, 300, False)
            sm.spawnMob(8644635, 200, 300, False)
            sm.spawnMob(8644634, 500, 300, False)
            sm.spawnMob(8644635, 800, 300, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060049, 0)
    else:
        sm.warpInstanceIn(993060049, 0)
sm.dispose()
