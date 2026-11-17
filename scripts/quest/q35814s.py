# [Limina] As Decreed (35814)
sm.onLayer(800, "bm0", 0, -80, 1, "Effect/Direction20.img/effect/BM3_soul/0", 4, False, -1, False)
started = sm.questStartDialogue(parentID)
sm.offLayer(500, "bm0", False)
if started:
    sm.warp(993070088, 0)
sm.dispose()
