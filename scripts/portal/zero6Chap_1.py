# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 327000000 [Mirror World] Ludibrium, in01: the Tower of the Future (your own copy while Tower of the Future, 40602, goes on)
if sm.hasQuest(40602) and sm.getQRValue(40614) != "1":
    sm.warpInstanceIn(327090210, 0)
else:
    sm.warp(327090200, 0)
sm.dispose()
