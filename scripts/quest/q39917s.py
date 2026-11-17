# [Cernium (After)] Defenders on the Wall (39917)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004515) # Hawkeye
sm.sendNext("You must defeat the swarms of Flora Soildiers. Talk to #bHawkeye#k in #b#m410000970##k again.")
if sm.questStartDialogue(parentID):
    offerWay(410000980, "Defeat the monsters there.")
sm.dispose()
