# 2420022 Tigun's Wealth (Tigun's House, Mirror World Ariant) - In Phantom's Name (41062): take the riches and leave
# the forged Phantom letter in their place.
if sm.hasQuest(41062) and not sm.hasItem(4033810):
    if sm.sendAskYesNo("#b(Bribes, piled high. Take Tigun's riches and leave the forged Phantom letter behind?)#k"):
        sm.giveItem(4033810)
        sm.setQRValue(41062, "clear2", "clear")
        sm.setQRValue(41062, "clear", "clear")
        sm.sendSayOkay("#b(You took Tigun's riches and left the forged letter signed 'Phantom'. "
                       "Now to bring them to Shati.)#k")
elif sm.hasQuest(41062):
    sm.sendSayOkay("#b(You already have Tigun's riches. Bring them to Shati.)#k")
else:
    sm.sendSayOkay("#b(Tigun's ill-gotten riches.)#k")
sm.dispose()
