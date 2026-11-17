# [Cernium (After)] Signs of the Prophecy (39916), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004515) # Hawkeye
if sm.questEndDialogue(parentID):
    sm.sendNext("The flora army's attacks are growing more fierce. The situation in #b#m410000970##k seems dire.")
    offerWay(410000970, "#p3004515# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
