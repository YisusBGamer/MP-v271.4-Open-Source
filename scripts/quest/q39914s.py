# [Cernium (After)] The People's Faith (39914)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004511) # Carlisle
sm.sendNext("Talk to #bCarlisle#k in #b#m410000500##k about #bSeren#k.")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004512# is waiting there.")
sm.dispose()
