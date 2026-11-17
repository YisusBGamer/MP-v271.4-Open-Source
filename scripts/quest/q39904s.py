# [Cernium (After)] Short Handed (39904)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004502) # Dawn Priest
sm.sendNext("Talk to the #bDawn Priest#k in #b#m410000550##k.")
if sm.questStartDialogue(parentID):
    offerWay(410000580, "#p3004503# is waiting there.")
sm.dispose()
