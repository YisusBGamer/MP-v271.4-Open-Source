# [Cernium (Before)] Worlds Collide (39802), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004426) # Checky
if sm.questEndDialogue(parentID):
    sm.sendNext("Someone called the 'sword's guardian' showed up just in time and helped with the battle.")
    offerWay(410000560, "#p3004402# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
