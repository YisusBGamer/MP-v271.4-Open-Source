# [Limina] Defying Destiny (35815)
sm.onLayer(800, "bm0", 0, -80, 1, "Effect/Direction20.img/effect/BM3_end/0", 4, False, -1, False)
started = sm.questStartDialogue(parentID)
sm.offLayer(500, "bm0", False)
if started:
    done = sm.questEndDialogue(35815)
sm.dispose()
