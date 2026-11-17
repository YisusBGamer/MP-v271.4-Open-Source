# [Cernium (After)] Unwanted Attention (39906), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004504) # Seren
if sm.questEndDialogue(parentID):
    sm.sendNext("You relayed the request about the damaged weapons to #bSeren#k in #b#m410000500##k.")
    offerWay(410000500, "#p3004504# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
