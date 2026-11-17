# Luminous: Aurora's Second Augury (quest 25592 "Finding the Second Augury")
# The Augury is found in Gold Beach, but the scripts that handed it over are missing from the client data. It's given
# when the region's story ends (quest 2975); q25592s.py gives it at once to players who finished the region before.
AUGURY = 4033329

if sm.hasQuest(25592) and not sm.hasItem(AUGURY):
    if sm.canHold(AUGURY):
        sm.giveItem(AUGURY)
        sm.chat("You found Aurora's Second Augury in Gold Beach. Return to Vieren in Harmony.")
    else:
        sm.chat("Aurora's Second Augury is here, but your Etc inventory is full.")
sm.dispose()
