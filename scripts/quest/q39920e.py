# [Cernium (After)] Salvation (39920), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004518) # Carlisle
if sm.questEndDialogue(parentID):
    sm.sendNext("The enemies mounted a ferocious assault. But in the end, the true #rowner of the holy sword#k stopped the #rcrimson lightning#k.")
    offerWay(410000810, "#p3004518# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
