# [Genesis Weapon] Traces of Lucid, Keeper of Nightmares (35664): hand-in. Act.img is empty, so the Remnant is taken here.
REMNANT = 4036464

if sm.questEndDialogue(parentID) and sm.hasItem(REMNANT):
    sm.consumeItem(REMNANT)
sm.dispose()
