# [Cernium (Before)] Fed Up (39815)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004416) # Seren
sm.sendNext("Tell #bSeren#k, in #b#m410000500##k, that you're ready to go to the #b#m410000760##k.")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004417# is waiting there.")
sm.dispose()
