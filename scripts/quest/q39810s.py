# [Cernium (Before)] Aaron the Librarian (39810)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004413) # Carlisle
sm.sendNext("#bCarlisle#k says he will introduce you to someone who can help track down the true owner of the holy sword. Meet #bAaron#k at the #b#m410000690##k.")
if sm.questStartDialogue(parentID):
    offerWay(410000690, "#p3004414# is waiting there.")
sm.dispose()
