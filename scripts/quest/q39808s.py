# [Cernium (Before)] The Holy Sword Azor (39808)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004409) # Carlisle
sm.sendNext("#bCarlisle#k wants to speak with you. Meet him at #b#m410000620##k.")
if sm.questStartDialogue(parentID):
    offerWay(410000680, "#p3004410# is waiting there.")
sm.dispose()
