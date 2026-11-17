# [Cernium (After)] Defensive Tactics (39921), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004516) # Carlisle
if sm.questEndDialogue(parentID):
    sm.sendNext("The palace has been reduced to a heap of rubble. #bSeren#k left with the broken sword.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
