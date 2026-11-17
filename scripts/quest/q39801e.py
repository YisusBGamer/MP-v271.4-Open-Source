# [Cernium (Before)] A Traveler from Beyond the Sea (39801), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004466) # Edea
if sm.questEndDialogue(parentID):
    sm.sendNext("Enemies are coming. You must hurry to the rally point. Go to #b#m410000520##k.")
    offerWay(410000520, "#p3004426# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
