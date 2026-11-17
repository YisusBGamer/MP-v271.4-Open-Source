# [Moonbridge] Neinheart's Briefing (35605)
sm.onLayer(800, "bm0", 0, -80, 1, "Effect/Direction20.img/effect/BMPre_Scroll/1", 4, False, -1, False)
sm.onLayer(800, "bm1", 0, -80, 2, "Effect/Direction20.img/effect/BMPre_Scroll/3", 4, False, -1, False)
started = sm.questStartDialogue(parentID)
sm.offLayer(500, "bm0", False)
sm.offLayer(500, "bm1", False)
if started:
    if sm.getFieldID() == 993060011 and sm.getChr().getInstance() is not None:
        if not sm.hasMobsInField():
            sm.spawnMob(8644633, -4400, 88, False)
            sm.spawnMob(8644632, -4100, 88, False)
            sm.spawnMob(8644633, -3800, 88, False)
            sm.spawnMob(8644632, -3500, 88, False)
    elif sm.getChr().getInstance() is not None:
        sm.warp(993060011, 0)
    else:
        sm.warpInstanceIn(993060011, 0)
sm.dispose()
