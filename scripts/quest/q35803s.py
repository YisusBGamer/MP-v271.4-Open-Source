# [Limina] A Tenacious One (35803)
started = sm.questStartDialogue(parentID)
if started:
    sm.setQRValue(35839, "dummy", "1")
    done = sm.questEndDialogue(35803)
sm.dispose()
