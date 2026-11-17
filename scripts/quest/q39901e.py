# [Cernium (After)] Burning Sky (39901), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004500) # Edea
if sm.questEndDialogue(parentID):
    sm.sendNext("After a long battle, the Flora Soldiers retreated.")
    offerWay(410000500, "#p3004500# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
