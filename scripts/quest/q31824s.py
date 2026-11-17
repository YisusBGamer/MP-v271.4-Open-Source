# id 31824 (Enter Romero), field 401020300
sm.setPlayerAsSpeaker()
if sm.sendAskYesNo("The transmitter is ringing. Ah. Ah! I GOTTA PICK UP!"):
    sm.sendNext("Bzzz...\r\n'Ah... ah... testing testing. Hey, can you hear me?'\r\n\r\n#b'Y-yes, I do... This doesn't sound like #p3001004#... Who's this?'#k")
    sm.setSpeakerID(3001005) # Romero
    sm.sendNext("'Yo, this is #p3001005#, the trade king of the merchant crew. Don't go nowhere, I'll come to you!'")
    sm.startQuest(31824)
    sm.completeQuest(31824)
else:
    sm.sendNext("On second thought...I WON'T pick it up.")
