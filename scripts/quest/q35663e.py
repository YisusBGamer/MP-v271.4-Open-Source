# [Genesis Weapon] Traces of Will, the King of Spiders (35663): hand-in. Act.img is empty, so the Remnant is taken here.
REMNANT = 4036463

if sm.questEndDialogue(parentID) and sm.hasItem(REMNANT):
    sm.consumeItem(REMNANT)
sm.dispose()
