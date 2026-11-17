# Mirror World side quest (Zero; Swordie Plus). Kid Sibling Wanted (41159): the stork (Crane, 2440013) can't bring
# Tae Soo a sibling, but lends a Pan Capture Rope - catch a Panda with it (the capture-item handler, ItemHandler).
ROPE, PANDA = 2270030, 4033822
sm.setSpeakerID(2440013)
if sm.hasItem(PANDA):
    sm.sendSayOkay("You caught one! Take it to the boy. I hope he likes it.")
elif not sm.hasItem(ROPE):
    sm.sendNext("A baby sibling? That's not... how that works, exactly. But I know where a cuddly little "
                "#bPanda#k lives, south of the village.")
    sm.giveItem(ROPE)
    sm.sendSayOkay("Use this #b#t2270030##k on a Panda to catch it, and bring it to the boy.")
else:
    sm.sendSayOkay("Use the #b#t2270030##k on a Panda to catch it.")
sm.dispose()
