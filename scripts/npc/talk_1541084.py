# Black Shadow (1541084), Maple Tree Hill 110000: retry Battle Against the Black Shadow (36322).
QUEST = 36322
if sm.hasQuest(QUEST) and sm.getQRValue(QUEST, "kill") != "1":
    if sm.sendAskYesNo("#b(The Black Shadow is still here. Fight it again?)#k"):
        sm.warpInstanceIn(993166023, 0)
else:
    sm.sendSayOkay("#b(Only wisps of dark smoke remain.)#k")
sm.dispose()
