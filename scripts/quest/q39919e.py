# [Cernium (After)] Library in Flames 2 (39919), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004529) # Aaron
if sm.questEndDialogue(parentID):
    sm.sendNext("You and #bAaron#k were able to rescue #bEdea#k. It seems she found a clue to a solution.")
    offerWay(410000800, "#p3004516# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
