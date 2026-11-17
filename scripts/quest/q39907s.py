# [Cernium (After)] A Rusty Blade (39907)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004504) # Seren
sm.sendNext("#bSeren#k, in #b#m410000500##k, has a request for you.")
if sm.questStartDialogue(parentID):
    offerWay(410000690, "#p3004505# is waiting there.")
sm.dispose()
