# [Limina] A Tenacious One 2 (35804)
sm.onLayer(800, "bm0", 0, -80, 1, "Effect/Direction20.img/effect/BM3_orca/0", 4, False, -1, False)
started = sm.questStartDialogue(parentID)
sm.offLayer(500, "bm0", False)
if started:
    sm.setQRValue(35839, "dummy", "1")
    done = sm.questEndDialogue(35804)
sm.dispose()
