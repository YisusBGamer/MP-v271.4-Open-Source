# Edelstein Outskirts 1 (931060010): Exposed (23639, Xenon). Beryl attacks.
if sm.hasQuest(23639) and sm.getQRValue(23719) != "1" and not sm.hasMobsInField():
    sm.spawnMob(9300636, 700, -14, False)
