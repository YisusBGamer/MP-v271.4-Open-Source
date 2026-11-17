# [Moonbridge] Clearing the Fog (35622)
started = sm.questStartDialogue(parentID)
if started:
    sm.onLayer(800, "bme0", 0, -80, 1, "Effect/Direction20.img/effect/BM1_hel/0", 4, False, -1, False)
    done = sm.questEndDialogue(35622)
    sm.offLayer(500, "bme0", False)
    if done:
        sm.warp(450009300, 0)
sm.dispose()
