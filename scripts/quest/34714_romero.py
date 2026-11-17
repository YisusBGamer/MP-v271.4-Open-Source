# Ultra Cannon Prepper (34714): the talk with Romero (Check.img npcSpeech script). Without this file the server would
# record the talk as "NpcSpeech=..." in record 34714, overwriting the transmitter's "1" the end check needs.
sm.setSpeakerID(3001034)  # Romero
if sm.getQRValue(parentID) != "1":
    sm.sendSayOkay("Use the #bD-03 Transmitter#k first, so the Heliseum branch knows who is calling.")
else:
    sm.sendNext("The Heliseum branch has a clean copy of that schematic. I'll read you the faded line.")
    sm.sendSayOkay("Take it to #bBeldar#k. He'll know where to find the material.")
    sm.setQRValue(parentID, "1")
sm.dispose()
