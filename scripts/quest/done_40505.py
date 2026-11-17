# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Done with quest 40505:
if sm.getQRValue(40513) != "1":
    sm.setQRValue(40513, "1")  # lets the next quest start
if sm.getFieldID() == 321190130:
    sm.warpInstanceOut(320000000, 0)
sm.dispose()
