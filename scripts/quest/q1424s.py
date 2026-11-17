# 1424 - 2nd job advancement (Pirate): accepted here, finished with q1424e

sm.setSpeakerID(1090000)
if sm.sendAskAccept("You're ready for your 2nd job advancement. I'll take you to my place, and we can talk there."):
    sm.startQuest(parentID)
    sm.warp(120000101)
