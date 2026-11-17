# [Cernium (After)] Salvation (39920)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004516) # Carlisle
sm.sendNext("Help #bCarlisle#k in the #b#m410000800##k.")
if sm.questStartDialogue(parentID):
    sm.sendNext("#b(You fight beside Carlisle against the Flora army. In the end the true owner of the holy sword stops the crimson lightning.)#k")
    sm.setQRValue(parentID, "clear", "1")
    offerWay(410000810, "#p3004518# is waiting there.")
sm.dispose()
