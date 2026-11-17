# Luminous: Aurora's Fourth Augury (quest 25605 "Finding the Fourth Augury")
# The Augury is found in Riena Strait, but the scripts that handed it over are missing from the client data. It's
# given when the region's story ends (quest 32192); q25605s.py gives it at once to players who finished the region
# before.
AUGURY = 4033332

if sm.hasQuest(25605) and not sm.hasItem(AUGURY):
    if sm.canHold(AUGURY):
        sm.giveItem(AUGURY)
        sm.chat("You found Aurora's Fourth Augury in Riena Strait. Return to Vieren in Harmony.")
    else:
        sm.chat("Aurora's Fourth Augury is here, but your Etc inventory is full.")
sm.dispose()
