# [Cernium (Before)] Cernium Unified (39818), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004428) # Edea
if sm.questEndDialogue(parentID):
    sm.sendNext("You promised Carlisle that you will visit Cernium in the not-too-distant future.")
    # The Cernium story ends with the first Sacred Symbol (GMS gives it with A Divine Power, 1484, which
    # the server completes at login)
    from net.swordie.ms.client.character import CerniumStoryModule
    CerniumStoryModule.giveSymbol(chr)
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
