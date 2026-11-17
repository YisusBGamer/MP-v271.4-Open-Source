# [Cernium (Before)] See You Around, Seren (39817)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004418) # Hawkeye
sm.sendNext("The situation at the #b#m410000630##k seems to be under control.  Talk to #bHawkeye#k.")
if sm.questStartDialogue(parentID):
    # Roland is stopped in 410000620 first (enter_410000620.py sets the record the hand-in needs)
    offerWay(410000620, "Roland was last seen there. Stop him, then tell #p3004416#.")
sm.dispose()
