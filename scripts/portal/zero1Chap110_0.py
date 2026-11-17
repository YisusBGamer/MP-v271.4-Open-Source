# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). 322090110 South Desert, in00: the rock over Phantom's hideout - "Open Sesame"
if sm.hasQuest(40104) and not sm.isComplete(40104):
    sm.setPlayerAsSpeaker()
    sm.sendNext("Open Sesame!")
    sm.warp(322090120, 0)
else:
    sm.chatScript("It's just a big rock.")
sm.dispose()
