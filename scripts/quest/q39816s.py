# [Cernium (Before)] Defend the Ramparts (39816)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004417) # Carlisle
sm.sendNext("You discovered a confrontation between #bCarlisle#k and the Ebonstar, which has thrown #b#m410000500##k#k into chaos.")
if sm.questStartDialogue(parentID):
    offerWay(410000630, "#p3004418# is waiting there.")
sm.dispose()
