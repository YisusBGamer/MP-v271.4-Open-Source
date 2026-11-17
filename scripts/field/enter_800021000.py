# Oni Forest Entrance (800021000), Mushroom Shrine Tales.
# Daidarabotchi's Second Message (58730) is handed in once you've seen the entrance empty of Oni:
# the scene's flag is "state" = 2 on record 58736.
if sm.hasQuest(58730) and sm.getQRValue(58736, "state") != "2":
    sm.setQRValue(58736, "state", "2")
    sm.chatScript("There isn't a single Oni here... Go back and tell Kino Konoko.")
