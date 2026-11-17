# [Cernium (After)] Invisible Scars (39903), handing it in

def offerWay(target, why):
    # the way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004502) # Dawn Priest
if sm.questEndDialogue(parentID):
    sm.sendNext("You cleared out #r#o8645124##k in #b#m410000550##k. You heard from the #bDawn Priest#k that the reason #bSeren#k cannot spread her wings is due to invisible scars on the heart, not external injuries.")
    offerWay(410000550, "#p3004502# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
