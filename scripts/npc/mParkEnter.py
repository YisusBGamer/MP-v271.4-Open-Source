# Gate (9071004), Monster Park: the dungeons are entered through the gates themselves (portals in00-in02,
# portal/mPark_in*.py); talking to it tells you which one fits your level.

sm.setSpeakerID(9071004)
lv = chr.getLevel()
if lv < 105:
    sm.sendSayOkay("Monster Park opens to adventurers of #bLevel 105#k and up. Come back when you're stronger!")
else:
    sm.sendSayOkay("Step into the gate for your level to enter a dungeon:\r\n\r\n"
                   "#b- Gate 1#k: Lv. 105-199 dungeons\r\n"
                   "#b- Gate 2#k: Arcane River, Lv. 200-265 (each needs that area's story)\r\n"
                   "#b- Gate 3#k: Grandis Advanced Dungeons, Lv. 260+\r\n\r\n"
                   "Clear as often as you like, free of charge. Monster Park is solo only.")
sm.dispose()
