# 36960 Aran, the Polearm Warrior (start): touch the polearm, then Maha
sm.setSpeakerID(1201001)
sm.sendNext("#b(You touch the #p1201001#. The polearm is supposed to be ice cold, but it feels so...warm. Makes you feel like your old memories are starting to return.)#k")
if sm.sendAskAccept("#b(Are you certain that you were the hero that wielded the #p1201001#? Surely it will react to you.)#k"):
    sm.startQuest(parentID)
    sm.sendSayOkay("#b(The polearm hums. Maha seems to want to talk to you.)#k")
sm.dispose()
