# [Cernium (After)] Coughing Fit (39908)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004505) # Edea
sm.sendNext("Talk to #bEdea#k, at the #b#m410000690##k, about the owner of the holy sword.")
if sm.questStartDialogue(parentID):
    offerWay(410000730, "Defeat the monsters there.")
sm.dispose()
