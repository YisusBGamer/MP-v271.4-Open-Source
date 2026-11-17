# 38707 Laboratory Investigation - end (Neinheart). Lost the keycard before logging in: Neinheart gives another
# (Quest.wz: "If you lose the keycard, ... talk to Neinheart").
NEINHEART = 3006553
KEYCARD = 4037197

if sm.hasQuest(parentID):
    sm.setSpeakerID(NEINHEART)
    sm.setBoxChat()
    if not sm.hasItem(KEYCARD):
        if sm.canHold(KEYCARD):
            sm.giveItem(KEYCARD)
        sm.sendNext("You lost the keycard? Here, Gaon had a spare. Log into the laboratory system with it.")
    elif sm.getQRValue(parentID, "clear") != "clear":
        sm.sendNext("Enter the laboratory from #b#m410007605##k and log into the system with Gaon's keycard.")
    else:
        sm.sendNext("Most of the data was deleted? Then someone wanted it gone... and that tells us something too.")
        if not sm.completeQuest(parentID):
            sm.chat("You haven't met all of this quest's requirements yet.")
sm.dispose()
