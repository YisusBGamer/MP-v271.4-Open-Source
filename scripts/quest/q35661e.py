# [Genesis Weapon] Traces of Lotus, the Wing Master (35661): hand-in. Act.img is empty, so the Remnant is taken here.
REMNANT = 4036461

if sm.questEndDialogue(parentID) and sm.hasItem(REMNANT):
    sm.consumeItem(REMNANT)
sm.dispose()
