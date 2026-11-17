# [Cernium (After)] A Rusty Blade (39907), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004505) # Edea
if sm.questEndDialogue(parentID):
    sm.sendNext("You delivered the holy water to #bEdea#k at the #b#m410000690##k.")
    offerWay(410000690, "#p3004505# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
