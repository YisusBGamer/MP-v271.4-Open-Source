# Vampiric Lantern (2430873), Luminous' story: "Double-click to move to the quest location in the blink of an eye."
# - One Who Sees Darkness (25554 / 25583): to the Giant Tree (101030000) for the Evil Eyes, and back again
# - The Ceremony (25555) / Secret Ritual (25584): to the Golem's Temple Entrance, by the puppet door (scr00)
# - Aurora's Gate (25556) / Harmony (25585): to Harmony (101000200)
# Not used up (notConsume).
GIANT_TREE = 101030000


def go(field, portal=0):
    # from inside an instance (a ritual room) a plain warp would land in a private copy of the destination
    if chr.getInstance() is not None:
        sm.warpInstanceOut(field, portal)
    else:
        sm.warp(field, portal)


if sm.hasQuest(25554) or sm.hasQuest(25583):
    if sm.getFieldID() == GIANT_TREE:
        go(sm.getReturnField())
    else:
        sm.setReturnField()
        go(GIANT_TREE)
elif sm.hasQuest(25555) or sm.hasQuest(25584):
    go(100040000, 8)
elif sm.hasQuest(25556) or sm.hasQuest(25585):
    sm.setReturnField()
    go(101000200)
else:
    sm.chat("Unlike normal lanterns, this lantern collects light.")
sm.dispose()
