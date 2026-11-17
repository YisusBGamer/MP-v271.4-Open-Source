# [Cernium (Before)] Worlds Collide (39802)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004426) # Checky
sm.sendNext("Talk to #bChecky#k in #b#m410000520:##k.")
if sm.questStartDialogue(parentID):
    offerWay(410000520, "Defeat the monsters there.")
sm.dispose()
