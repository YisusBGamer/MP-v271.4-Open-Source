# [Cernium (After)] End of an Alliance (39913)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004508) # Edea
sm.sendNext("Talk to #bEdea#k #b#m410000770##k.")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004511# is waiting there.")
sm.dispose()
