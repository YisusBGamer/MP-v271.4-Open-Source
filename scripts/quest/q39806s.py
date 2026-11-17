# [Cernium (Before)] God of Red Flame (39806)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004406) # Dawn Priest
sm.sendNext("Talk to the #bDawn Priest#k at the #rWestern City Ramparts#k.")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004407# is waiting there.")
sm.dispose()
