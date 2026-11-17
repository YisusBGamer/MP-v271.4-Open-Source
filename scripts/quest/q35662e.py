# [Genesis Weapon] Traces of Damien, the Sword of Destruction (35662): hand-in. Act.img is empty, so the Remnant is taken here.
REMNANT = 4036462

if sm.questEndDialogue(parentID) and sm.hasItem(REMNANT):
    sm.consumeItem(REMNANT)
sm.dispose()
