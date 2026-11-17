# [Genesis Weapon] Traces of Arkarium, the Guardian of Time (35659): hand-in. Act.img is empty, so the Remnant is taken here.
REMNANT = 4036458

if sm.questEndDialogue(parentID) and sm.hasItem(REMNANT):
    sm.consumeItem(REMNANT)
sm.dispose()
