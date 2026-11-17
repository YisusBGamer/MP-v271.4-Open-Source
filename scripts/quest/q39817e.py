# [Cernium (Before)] See You Around, Seren (39817), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004416) # Seren
if sm.questEndDialogue(parentID):
    sm.sendNext("You met #bRoland#k in #b#m410000620##k He had completely lost his faith. You lost sight of him, possibly forever, when the city gate was blasted.")
    offerWay(410000500, "#p3004427# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
