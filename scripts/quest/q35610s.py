# [Moonbridge] Deeper Resolution (35610)
started = sm.questStartDialogue(parentID)
if started:
    done = sm.questEndDialogue(35610)
    if done:
        sm.warp(993060074, 0)
sm.dispose()
