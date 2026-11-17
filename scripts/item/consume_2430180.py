# Barrier Totem (2430180), for 'Barrier Totem' (3192), El Nath.
# Quest.wz: "If you put up the Barrier Totem near Lich, it should cut off his forbidden magic. Put the totem up at
# #m211041400#." / "You put up the Barrier Totem and reported to Krag." Item: "Double-click it to place it when you
# are in the vicinity of Lich." Lich is behind the Mystic Gate in that map (931050428). Krag checks the quest's record
# 3192 = "1" (Check.img infoNumber).
QUEST = 3192
TOTEM = 2430180
FOREST_OF_DEAD_TREES_IV = 211041400
LICH_ZONE = 931050428

if sm.hasQuest(QUEST) and sm.getQRValue(QUEST) != "1":
    if sm.getFieldID() in (FOREST_OF_DEAD_TREES_IV, LICH_ZONE):
        sm.consumeItem(TOTEM)
        sm.setQRValue(QUEST, "1", False)
        sm.chatScript("You put up the Barrier Totem.")
    else:
        sm.chatScript("Put the totem up at Forest of Dead Trees IV.")
sm.dispose()
