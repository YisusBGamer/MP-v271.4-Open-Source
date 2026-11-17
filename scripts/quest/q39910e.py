# [Cernium (After)] Internal Conflict (39910), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004508) # Edea
if sm.questEndDialogue(parentID):
    sm.sendNext("Seren is determined to save her old comrade, #bGilmore#k, who's being held hostage.")
    offerWay(410000770, "#p3004509# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
