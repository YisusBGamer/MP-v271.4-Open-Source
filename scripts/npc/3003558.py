# Melange (3003558), Radiant Throne. During Mirrors in Mirrors (37871) he sends you back into the mirror within the
# mirror (450007510, field/enter_450007510).
MELANGE = 3003558

sm.setSpeakerID(MELANGE)
sm.setBoxChat()
if sm.hasQuest(37871) and not sm.isComplete(37871):
    if sm.sendAskYesNo("#face0#Will is inside the mirror. Are you going back in?"):
        sm.warpInstanceIn(450007510, 0)
else:
    sm.sendSayOkay("#face0#The power of the #rTranscendents of two worlds#k... We have to be ready.")
sm.dispose()
