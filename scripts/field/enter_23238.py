# 931050200 Training Room Storage: Black Wing Spy! (Demon 23238) - the spy hiding in the dark (9300456).
if sm.hasQuest(23238) and not sm.hasMobsInField():
    pos = chr.getPosition()
    sm.spawnMob(9300456, pos.getX() + 250, pos.getY(), False)
