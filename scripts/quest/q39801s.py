# [Cernium (Before)] A Traveler from Beyond the Sea (39801)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004203) # Neinheart
sm.sendNext("Someone came walking out of the fog. You arrived at #b#m410000510##k with Carlisle.  Talk to Checky.")
if sm.questStartDialogue(parentID):
    offerWay(410000510, "#p3004466# is waiting there.")
sm.dispose()
