# Whispershot (Kain 39403): the Regulator's start dialogue; the Wooden Boxes are placed next to you.
if sm.questStartDialogue(parentID) and sm.getFieldID() == 410000416 and not sm.hasMobsInField(410000416, 2400650):
    pos = chr.getPosition()
    for dx in (150, 260, 370):
        sm.spawnMob(2400650, pos.getX() + dx, pos.getY(), False)
sm.dispose()
