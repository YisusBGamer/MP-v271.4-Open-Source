# [Cernium (Before)] Monster Gull (39805)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004404) # Little Kid
sm.sendNext("A #bYoungster#k in the left side of Cernium Square greets you.")
if sm.questStartDialogue(parentID):
    offerWay(410000590, "Defeat the monsters there.")
sm.dispose()
