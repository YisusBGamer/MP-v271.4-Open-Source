# [Cernium (Before)] Cernium, the Holy Land (39803)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004402) # Carlisle
sm.sendNext("Learn about #rCernium#k from #bCarlisle#k at the #bHoly Land Vantage Point#k.")
if sm.questStartDialogue(parentID):
    offerWay(410000500, "#p3004403# is waiting there.")
sm.dispose()
