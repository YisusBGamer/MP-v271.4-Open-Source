# [Cernium (Before)] Cernium Unified (39818)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004427) # Carlisle
sm.sendNext("You successfully defended Cernium. Talk to #bCarlisle#k.")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004428# is waiting there.")
sm.dispose()
