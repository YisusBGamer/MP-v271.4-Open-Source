# [Cernium (Before)] The Flightless Celestial (39812)

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004415) # Edea
sm.sendNext("Is it just you, or are things awkward in here? It seems #bSeren#k has something to ask you.")
if sm.questStartDialogue(parentID):
    offerWay(410000700, "Defeat the monsters there.")
sm.dispose()
