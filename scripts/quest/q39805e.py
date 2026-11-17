# [Cernium (Before)] Monster Gull (39805), handing it in

def offerWay(target, why):
    # The way to where the story goes on (most Cernium maps join up; this saves the walk)
    if target and sm.getFieldID() != target and sm.sendAskYesNo(why + "\r\n\r\nShall I take you to #b#m" + str(target) + "##k?"):
        sm.warp(target)

sm.setSpeakerID(3004405) # Hawkeye
if sm.questEndDialogue(parentID):
    sm.sendNext("You chased away the #r#o8645125:##k.  #bHawkeye#k told you to go to #b#m410000570##k.")
    offerWay(410000580, "#p3004406# has something to tell you.")
else:
    sm.sendSayOkay("You haven't finished yet. Defeat the monsters first.")
sm.dispose()
