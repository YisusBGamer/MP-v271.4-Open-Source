# [Moonbridge] Defensive Strategy (35619)
started = sm.questStartDialogue(parentID)
if started:
    if sm.getFieldID() == 993060046 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644634, -1600, 300, False)
            sm.spawnMob(8644635, -1350, 300, False)
            sm.spawnMob(8644634, -1100, 300, False)
            sm.spawnMob(8644635, -850, 300, False)
            sm.spawnMob(8644634, -350, 300, False)
            sm.spawnMob(8644635, -100, 300, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060046, 0)
    else:
        sm.warpInstanceIn(993060046, 0)
sm.dispose()
