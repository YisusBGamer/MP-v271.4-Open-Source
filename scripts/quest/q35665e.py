# [Genesis Weapon] Traces of Verus Hilla the Red Witch (35665): hand-in. Act.img is empty, so the Remnant is taken here.
REMNANT = 4036465

if sm.questEndDialogue(parentID) and sm.hasItem(REMNANT):
    sm.consumeItem(REMNANT)
sm.dispose()
