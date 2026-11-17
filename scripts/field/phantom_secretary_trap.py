# 931050220 Francis's Room (see CustomFieldScripts): The Sneakiest Secretary (Phantom 25416) - it was a trap:
# 10 Black Wing Henchmen (9300499).
if sm.hasQuest(25416) and chr.getInstance() is not None and not sm.hasMobsInField(931050220, 9300499):
    pos = chr.getPosition()
    for i in range(10):
        sm.spawnMob(9300499, pos.getX() + 100 + (i % 5) * 90, pos.getY(), False)
    sm.chat("It was a trap! Defeat the Black Wing Henchmen.")
