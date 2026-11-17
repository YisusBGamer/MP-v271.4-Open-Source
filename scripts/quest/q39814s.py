# [Cernium (Before)] Delicious Candy (39814)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004413) # Carlisle
sm.sendNext("Talk to #bCarlisle#k in the #b#m410000670##k about what to do next.")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004416# is waiting there.")
sm.dispose()
