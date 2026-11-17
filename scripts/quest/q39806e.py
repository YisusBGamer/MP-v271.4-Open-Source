# [Cernium (Before)] God of Red Flame (39806), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004407) # Little Kid
if sm.questEndDialogue(parentID):
    sm.sendNext("You saw the familiar #bYoungster#k in #b#m410000500##k.")
    offerWay(410000500, "#p3004407# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
