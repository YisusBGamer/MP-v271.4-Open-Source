# [Cernium (Before)] A Curious Feather (39813)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004415) # Edea
sm.sendNext("Wrap up your investigation with #bEdea#k at the #b#m410000690##k.")
if sm.questStartDialogue(parentID):
    offerWay(410000670, "#p3004413# is waiting there.")
sm.dispose()
