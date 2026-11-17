# [Cernium (After)] Coughing Fit (39908), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004505) # Edea
if sm.questEndDialogue(parentID):
    sm.sendNext("You searched for documents related to the Seal Stone. Awakening the holy artifact requires the will and #rdetermination#k of many. The power within a relic chooses its owner.  In other words, only the #rChosen One#k can take possession of the sword.")
    offerWay(410000690, "#p3004506# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
