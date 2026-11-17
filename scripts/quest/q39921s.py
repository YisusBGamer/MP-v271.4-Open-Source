# [Cernium (After)] Defensive Tactics (39921)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004518) # Carlisle
sm.sendNext("Ask #bCarlisle#k, in #b#m410000810##k, for a status report.")
if sm.questStartDialogue(parentID):
    offerWay(410000800, "#p3004516# is waiting there.")
sm.dispose()
