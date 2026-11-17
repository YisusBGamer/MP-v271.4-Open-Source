CUTTER = 1096005

sm.setSpeakerID(CUTTER)
if sm.sendAskAccept("Right, we're all set! Time to go. Do you have everything! Ha! That's a joke. Doesn't matter if you do or not. Let's GO."):
    sm.startQuest(parentID)
    sm.warp(912060200, 0)
else:
    sm.sendNext("You're not done here? What could you POSSIBLY want to do on a mostly-deserted island?")
    sm.dispose()