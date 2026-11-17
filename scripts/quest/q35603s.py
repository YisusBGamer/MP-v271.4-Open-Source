# [Moonbridge] Back to Maple World (35603)
sm.onLayer(800, "bm0", 0, -80, 1, "Effect/Direction20.img/effect/BMPre_flashBack/0", 4, False, -1, False)
started = sm.questStartDialogue(parentID)
sm.offLayer(500, "bm0", False)
if started:
    done = sm.questEndDialogue(35603)
    if done:
        sm.warp(993060010, 0)
sm.dispose()
