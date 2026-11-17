# [Cernium (After)] Another Celestial (39912)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004527) # Checky
sm.sendNext("#bChecky#k is ready to start the next stage of the plan at the #b#m993150071##k.")
if sm.questStartDialogue(parentID):
    sm.sendNext("#b(With Checky, you hold off the army at the Flora encampment. You escape with Gilmore, but the emblem stays out of reach.)#k")
    sm.setQRValue(parentID, "clear", "1")
    offerWay(410000770, "#p3004509# is waiting there.")
sm.dispose()
