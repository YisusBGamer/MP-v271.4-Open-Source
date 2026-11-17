# [Cernium (After)] Internal Conflict (39910)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004507) # Court Knight
sm.sendNext("A #bCourt Knight#k barged into the #b#m410000690##k on an urgent task. What could it be?")
if sm.questStartDialogue(parentID):
    offerWay(410000770, "#p3004508# is waiting there.")
sm.dispose()
