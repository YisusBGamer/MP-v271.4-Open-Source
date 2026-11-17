# [Moonbridge] Nautilus in Need (35616)
started = sm.questStartDialogue(parentID)
if started:
    done = sm.questEndDialogue(35616)
    if done:
        sm.warp(993060045, 0)
sm.dispose()
