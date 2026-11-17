# [Cernium (After)] Make it Happen (39909)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004506) # Seren
sm.sendNext("Talk with #bSeren#k at the #b#m410000690##k about the owner of the holy sword.")
if sm.questStartDialogue(parentID):
    offerWay(410000730, "Defeat the monsters there.")
sm.dispose()
