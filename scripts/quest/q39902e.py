# [Cernium (After)] Keeping Options Open (39902), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004501) # Court Knight
if sm.questEndDialogue(parentID):
    sm.sendNext("On your way to the #b#m410000510##k, you met a Court Knight in trouble at the #b#m410000560##k.")
    offerWay(410000560, "#p3004501# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
