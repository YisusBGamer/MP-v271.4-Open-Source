# 410000416 Toolen City East Side 1 (see CustomFieldScripts): Whispershot (Kain 39403) - destroy 3 Wooden Boxes
# (2400650) for the Regulator's condition check. The map doesn't place them by itself.
if sm.hasQuest(39403) and not sm.hasMobsInField(410000416, 2400650):
    pos = chr.getPosition()
    for dx in (150, 260, 370):
        sm.spawnMob(2400650, pos.getX() + dx, pos.getY(), False)
