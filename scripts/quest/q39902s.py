# [Cernium (After)] Keeping Options Open (39902)

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004500) # Edea
sm.sendNext("Talk to #bEdea#k in #b#m410000500##k.")
if sm.questStartDialogue(parentID):
    offerWay(410000560, "#p3004501# is waiting there.")
sm.dispose()
