# [Cernium (After)] End of an Alliance (39913), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004511) # Carlisle
if sm.questEndDialogue(parentID):
    sm.sendNext("#bSeren#k stood by her decision to hand over the holy sword to #bGilmore#k. #bEdea#k, disappointed, declared an end to the alliance with the kingdom of Cernium.")
    offerWay(410000500, "#p3004511# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
