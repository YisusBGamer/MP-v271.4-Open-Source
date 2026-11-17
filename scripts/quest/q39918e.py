# [Cernium (After)] Library in Flames (39918), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004517) # Court Knight
if sm.questEndDialogue(parentID):
    sm.sendNext("There's been an urgent request from a #bCourt Knight#k in #b#m410000830##k.")
    offerWay(410000830, "#p3004517# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
