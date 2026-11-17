# [Cernium (Before)] Dawn and Flame (39807)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004407) # Little Kid
sm.sendNext("You see a familiar #bYoungster#k in #bCernium Square#k.")
if sm.questStartDialogue(parentID):
    offerWay(410000640, "Defeat the monsters there.")
sm.dispose()
