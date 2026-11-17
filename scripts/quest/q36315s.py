# 36315 Olive's Special Training: beat the Blue Mushmom in Olive's training grounds (Blue Mushmom Forest 993166100, own
# copy; field/enter_993166100.py runs the fight). Forfeit and accept again to retry.
sm.setSpeakerID(1541003)
sm.sendNext("Hey! Now that we've got the Seal Stone back, don't think you can slack off. You're still way too weak!")
if not sm.sendAskAccept("I found a perfect training ground. There's a #rBlue Mushmom#k there. Beat it, and maybe I'll admit you're not totally useless."):
    sm.dispose()
if not sm.hasQuest(parentID):
    sm.startQuest(parentID)
sm.warpInstanceIn(993166100, 0)
sm.dispose()
