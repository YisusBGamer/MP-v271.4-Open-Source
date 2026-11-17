# 102000003 / 103000003 / 910350220 (job instructors' rooms). In the Kerning City Hideout (103000003), the Dark Lord
# proves he's no weakling for A Difficult Decision (2643, Dual Blade story): his clone fights you.
if sm.getFieldID() == 103000003 and sm.hasQuest(2643) and not sm.isComplete(2643) \
        and chr.getField().getLifeByTemplateId(9300528) is None:
    sm.chatScript("The Dark Lord sends a shadow clone at you. Show him what a Dual Blade can do!")
    sm.spawnMob(9300528, -100, 142, False)
# Investigating the Jazz Bar (2621, Dual Blade story): "Ryden wants you to search the Jazz Bar while he lures the Dark
# Lord outside" / "You found the Dark Lord's Diary" (the quest's own text). Searching the Hideout finds the diary.
if sm.getFieldID() == 103000003 and sm.hasQuest(2621) and not sm.hasItem(4033183):
    if sm.canHold(4033183):
        sm.giveItem(4033183)
        sm.chatScript("While Ryden keeps the Dark Lord busy outside, you search every corner... and find the Dark Lord's Diary.")
    else:
        sm.chatScript("Make some room in your Etc inventory first.")
