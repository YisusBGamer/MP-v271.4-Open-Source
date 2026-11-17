# 922030001 Frog House: Rage (Evan 22596) - Gentleman (9300393).
if sm.hasQuest(22596) and not sm.hasMobsInField():
    pos = chr.getPosition()
    sm.spawnMob(9300393, pos.getX() + 250, pos.getY(), False)
