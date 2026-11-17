# 38208 [Karote] Vessel of the Adversary - end (Belle, Halfway Up)
BELLE = 3005245

if sm.hasQuest(parentID):
    sm.setSpeakerID(BELLE)
    sm.setBoxChat()
    sm.sendNext("Scraps, scribbles, half a diagram... whoever worked here didn't want anyone reading their notes.")
    sm.sendNext("Neinheart's gone all quiet. That's never a good sign, is it?")
    sm.completeQuest(parentID)
sm.dispose()
