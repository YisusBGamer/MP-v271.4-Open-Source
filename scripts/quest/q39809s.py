# [Cernium (Before)] Seren vs Edea (39809)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004411) # Holy Sword Azor
sm.sendNext("Place your hand on the #bholy sword Azor#k, as #bCarlisle#k requested.")
if sm.questStartDialogue(parentID):
    offerWay(410000670, "#p3004412# is waiting there.")
sm.dispose()
