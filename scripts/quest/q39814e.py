# [Cernium (Before)] Delicious Candy (39814), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004416) # Seren
if sm.questEndDialogue(parentID):
    sm.sendNext("You resumed your investigation with #bSeren#k in #b#m410000500##k. You met the Youngster, who supposedly saw the emblem, but then their parents came and dragged them off. Now the only place left to look is #b#m410000760##k.")
    offerWay(410000500, "#p3004416# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet.")
sm.dispose()
