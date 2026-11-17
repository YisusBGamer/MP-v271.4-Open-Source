# [Cernium (Before)] Dawn and Flame (39807), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004408) # Checky
if sm.questEndDialogue(parentID):
    sm.sendNext("You chased away the #r#o8645126##k from the #b#m410000640##k #bChecky#k made.  Meanwhile, the guardian and the priests are in a dispute over enforcement of the law.")
    offerWay(410000620, "#p3004409# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
