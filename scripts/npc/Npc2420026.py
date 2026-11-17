# 2420026 Adequate Wall (Mirror World Ariant, west) - Sand Bandits Test 1 (40106): put up the anti-queen poster.
# Record 40118 = 1 marks it (the wall then shows the poster, Npc.wz condition1).
if sm.hasQuest(40106) and sm.getQRValue(40118) != "1":
    if sm.sendAskYesNo("#b(A good, wide wall where everyone passing by will see it. Put up the anti-queen poster here?)#k"):
        sm.setQRValue(40118, "1")
        sm.sendSayOkay("#b(You put up the poster. Report back to Sejan at the Sand Bandits' hideout.)#k")
elif sm.getQRValue(40118) == "1":
    sm.sendSayOkay("#b(The anti-queen poster is up for everyone to see.)#k")
else:
    sm.sendSayOkay("#b(A plain sandstone wall.)#k")
sm.dispose()
