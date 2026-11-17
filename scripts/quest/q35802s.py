# [Limina] World's Sorrow (35802)
started = sm.questStartDialogue(parentID)
if started:
    sm.setQRValue(35839, "dummy", "1")
    done = sm.questEndDialogue(35802)
sm.dispose()
