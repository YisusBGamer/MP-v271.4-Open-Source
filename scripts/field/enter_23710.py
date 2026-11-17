# Silo (931060040): Clash of the Xenoroids (23710, Xenon). Meta-Beryl is waiting.
if sm.hasQuest(23710) and sm.getQRValue(23723) != "1" and not sm.hasMobsInField():
    sm.spawnMob(9300639, 300, 43, False)
