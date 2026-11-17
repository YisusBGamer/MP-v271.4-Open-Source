# Luminous: Aurora's First Augury (quest 25589 "Finding the First Augury")
# The Augury is found in the Ellinel Fairy Academy, but the scripts that handed it over are missing from the client
# data. It's given when the region's story ends (quest 32129); q25589s.py gives it at once to players who finished the
# region before.
AUGURY = 4033328

if sm.hasQuest(25589) and not sm.hasItem(AUGURY):
    if sm.canHold(AUGURY):
        sm.giveItem(AUGURY)
        sm.chat("You found Aurora's First Augury in the Ellinel Fairy Academy. Return to Vieren in Harmony.")
    else:
        sm.chat("Aurora's First Augury is here, but your Etc inventory is full.")
sm.dispose()
