# Bookcase (2400020, Npc.wz script zeroReading) | Zero's Temple (320000000), the Cabinet
# Introducing the Cabinet (41905, hidden guide quest) is handed in here once its own record says clear=clear.
sm.setSpeakerID(parentID)
sm.sendSayOkay("#b(The Cabinet. The books in it can be read here.)#k")
if sm.hasQuest(41905) and sm.getQRValue(41905, "clear") != "clear":
    sm.setQRValue(41905, "clear", "clear")
