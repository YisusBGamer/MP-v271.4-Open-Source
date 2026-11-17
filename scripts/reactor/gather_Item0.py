# Pig Pasture's herb plants (reactor 1209002, 120010000): The Best Seasoning (2904) asks for a Herb Leaf, picked from them
HERB_QUEST = 2904
HERB_LEAF = 4033063

if sm.hasQuest(HERB_QUEST) and not sm.hasItem(HERB_LEAF):
    if sm.canHold(HERB_LEAF):
        sm.giveItem(HERB_LEAF)
        sm.chat("You picked a fragrant Herb Leaf.")
    else:
        sm.chat("Your Etc inventory is full.")
sm.dispose()
