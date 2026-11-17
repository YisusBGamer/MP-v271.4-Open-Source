# 36322 Battle Against the Black Shadow - end (field/enter_993166023.py usually finishes it)
sm.setPlayerAsSpeaker()
if sm.completeQuest(parentID):
    sm.setQRValue(36300, "84", "h0")
    sm.sendSayOkay("#b(You defeated the Black Shadow.)#k")
else:
    sm.sendSayOkay("#b(The Black Shadow is still out there. Click it on Maple Tree Hill to fight again.)#k")
sm.dispose()
