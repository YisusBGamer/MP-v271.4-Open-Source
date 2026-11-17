# [Cernium (Before)] Seren vs Edea (39809), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004412) # Edea
if sm.questEndDialogue(parentID):
    sm.sendNext("You told the others that all you felt from the holy sword was #rhostility#k. Then #bSeren#k, the sword's guardian, walked in at the worst possible time.")
    offerWay(410000670, "#p3004413# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
