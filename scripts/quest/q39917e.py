# [Cernium (After)] Defenders on the Wall (39917), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004514) # Carlisle
if sm.questEndDialogue(parentID):
    sm.sendNext("The Flora Soldiers have mounted a fierce attack. Just as in the prophecy, crimson thunderclouds cover the sky. Carlisle has given the order to abandon the ramparts and fall back.")
    offerWay(410000800, "#p3004516# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
