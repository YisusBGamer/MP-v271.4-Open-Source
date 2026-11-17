# [Cernium (Before)] Ancient Ghosts (39811)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004414) # Aaron
sm.sendNext("Speak to #bAaron#k at the #rRoyal Library#k about the owner of the holy sword.")
if sm.questStartDialogue(parentID):
    offerWay(410000700, "Defeat the monsters there.")
sm.dispose()
