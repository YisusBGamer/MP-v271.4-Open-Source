# [Moonbridge] Shipwreck Investigation (35626)
started = sm.questStartDialogue(parentID)
if started:
    done = sm.questEndDialogue(35626)
    if done:
        sm.warp(993060059, 0)
sm.dispose()
