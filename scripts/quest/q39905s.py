# [Cernium (After)] Monstrous Gulls (39905)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004503) # Hawkeye
sm.sendNext("Ask #bHawkeye#k, at the #b#m410000580##k, if there's anything you can do to help.")
if sm.questStartDialogue(parentID):
    offerWay(410000590, "Defeat the monsters there.")
sm.dispose()
