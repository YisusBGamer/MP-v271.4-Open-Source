# [Limina] The Erda Surges (35801)
started = sm.questStartDialogue(parentID)
if started:
    sm.setQRValue(35839, "dummy", "1")
    done = sm.questEndDialogue(35801)
sm.dispose()
