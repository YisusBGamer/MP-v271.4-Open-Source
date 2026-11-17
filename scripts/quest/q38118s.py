# 38118 [Hotel Arcus] The Beep of Treasure - start (Belle, Train with No Destination 3). Handed in by reaching the
# Train Locomotive (field/enter_410003210).
BELLE = 3005014

sm.setSpeakerID(BELLE)
sm.setBoxChat()
sm.sendNext("Phew, that's the last of the bots. Wait... do you hear that? My treasure detector is going wild!")
sm.sendNext("It's coming from the front of the train. Something big is up there, I just know it!")
if sm.sendAskAccept("Let's head for the #b#m410003210##k and see what's making it beep!"):
    sm.startQuest(parentID)
    sm.sendNext("Keep moving east through the cars. The locomotive's at the very front.")
sm.dispose()
