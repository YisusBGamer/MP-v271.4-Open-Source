# [Cernium (After)] Signs of the Prophecy (39916)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004513) # Nova Soldier
sm.sendNext("Ask the #bNova Soldier#k at the #b#m410000900##k how you can help.")
if sm.questStartDialogue(parentID):
    offerWay(410000970, "#p3004515# is waiting there.")
sm.dispose()
