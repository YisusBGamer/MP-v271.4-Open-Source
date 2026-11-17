# [Cernium (Before)] Defend the Ramparts (39816), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004418) # Hawkeye
if sm.questEndDialogue(parentID):
    sm.sendNext("You've successfully defended the #b#m410000630##k.")
    offerWay(410000630, "#p3004418# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
