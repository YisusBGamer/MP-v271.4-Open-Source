# Faraway Land (38032, Shade) - Dr. Bruce, the last stop of the Maple Map chase (Stan -> Camila -> Jay -> Dr. Bruce)
# The client runs this script when Shade talks to Dr. Bruce (Check.img npcSpeech 4). No official lines exist for it;
# written from the quest's own (Jay: "Dr. Bruce said he needed it for research").
BRUCE = 1012111
MAP = 4034060  # Maple Map

sm.setSpeakerID(BRUCE)
if sm.hasItem(MAP):
    sm.sendSayOkay("You already have the #t4034060#. Chief Stan is waiting for it.")
elif not sm.canHold(MAP):
    sm.sendSayOkay("Make some room in your Etc inventory first.")
else:
    sm.sendNext("Chief Stan's #b#t4034060##k? Ah, yes, I borrowed it for my research on the monsters of Victoria Island. "
                "I'm finished with it now. Please take it back to him, with my thanks.")
    sm.giveItem(MAP)
    sm.setQRValue(parentID, "NpcSpeech=10121061/10121082/10121093/10121114")
sm.dispose()
