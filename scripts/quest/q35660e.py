# [Genesis Weapon] Traces of the Tyrant Magnus (35660): hand-in. Act.img is empty, so the Remnant is taken here.
REMNANT = 4036460

if sm.questEndDialogue(parentID) and sm.hasItem(REMNANT):
    sm.consumeItem(REMNANT)
sm.dispose()
