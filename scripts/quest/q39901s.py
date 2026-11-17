# [Cernium (After)] Burning Sky (39901)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004431) # Edea
sm.sendNext("#bEdea#k has something to tell you.")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004500# is waiting there.")
sm.dispose()
