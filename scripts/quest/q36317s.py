# 36317 Back to the Gate of the Present (Athena Pierce): replacing the cracked Seal Stone goes wrong. Sets the quest's
# flag (dummy = 1) and ends by itself.
sm.setSpeakerID(1012100)
sm.sendNext("The Gate is open and its Seal Stone is cracked? Then the Black Mage's seal is weakening!")
if not sm.sendAskAccept("Take the #bSeal Stone of Victoria Island#k and put it in place of the cracked one. It may be enough to hold the seal."):
    sm.dispose()
sm.startQuest(parentID)
sm.setPlayerAsSpeaker()
sm.sendNext("#b(You return to the Gate of the Present and hold the Seal Stone of Victoria Island up to the cracked stone.)#k")
sm.sendSay("#b(The two stones flare with light... and a wave of dark energy bursts out of the Gate. The Seal Stone of Victoria Island is thrown back into your hands.)#k")
sm.setQRValue(parentID, "dummy", "1")
sm.completeQuest(parentID)
sm.sendSayOkay("#b(Did you just break the Black Mage's seal?)#k")
sm.dispose()
