# [Cernium (Before)] The Holy Sword Azor (39808), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004410) # Carlisle
if sm.questEndDialogue(parentID):
    sm.sendNext("You stood before the #rholy sword Azor#k with #bCarlisle#k.")
    offerWay(410000680, "#p3004411# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
