# [Cernium (Before)] The Flightless Celestial (39812), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004415) # Edea
if sm.questEndDialogue(parentID):
    sm.sendNext("You found out more about the #bholy sword#k. #bEdea#k asked if #bSeren#k couldn't also qualify for the role of the Chosen One. However, #bSeren#k cannot spread her wings, due to an old injury.")
    offerWay(410000690, "#p3004415# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
