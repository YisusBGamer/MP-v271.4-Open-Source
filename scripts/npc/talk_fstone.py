# Floating Rock Fragment (2131011) in the Small Forest (300000100). [Ellin Forest] Yuris the Mechanic Elf (36344):
# Yuris's lost Floating Rock Fragment (4036853), an orange gem.
FRAGMENT = 4036853
QUEST = 36344

if sm.hasQuest(QUEST) and not sm.hasItem(FRAGMENT):
    if sm.canHold(FRAGMENT, 1):
        sm.giveItem(FRAGMENT)
        sm.chat("You picked up an orange gem: the Floating Rock Fragment. Take it back to Yuris.")
    else:
        sm.chat("Make room in your Etc inventory first.")
else:
    sm.chat("An orange stone glimmers in the grass.")
sm.dispose()
