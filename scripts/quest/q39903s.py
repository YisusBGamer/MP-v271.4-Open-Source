# [Cernium (After)] Invisible Scars (39903)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004501) # Court Knight
sm.sendNext("The #bCourt Knights#k of the #b#m410000560##k look like they're in need of help.")
if sm.questStartDialogue(parentID):
    offerWay(410000540, "Defeat the monsters there.")
sm.dispose()
