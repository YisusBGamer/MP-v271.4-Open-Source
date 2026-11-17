# [Cernium (After)] Unwanted Attention (39906)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004503) # Hawkeye
sm.sendNext("Talk to #bHawkeye#k again at the #b#m410000580##k.")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004504# is waiting there.")
sm.dispose()
