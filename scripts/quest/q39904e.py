# [Cernium (After)] Short Handed (39904), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004503) # Hawkeye
if sm.questEndDialogue(parentID):
    sm.sendNext("You met with #bHawkeye#k, who's short handed, at the #b#m410000580##k.")
    offerWay(410000580, "#p3004503# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
