# 993070078: monsters for the story fight of 35846 (35809, 35810 before)
if ((sm.hasQuest(35846) and sm.getQRValue(35846, "rescued") != "1") or
        (sm.hasQuest(35810) and sm.getQRValue(35834, "q35810") != "1")) and not sm.hasMobsInField():
    sm.spawnMob(8645032, -1500, 5, False)
    sm.spawnMob(8645032, -900, 5, False)
    sm.spawnMob(8645032, 0, 5, False)
    sm.spawnMob(8645032, 900, 5, False)
    sm.spawnMob(8645032, 1400, 5, False)
elif sm.hasQuest(35809) and sm.getQRValue(35834, "q35809") != "1" and not sm.hasMobsInField():
    sm.spawnMob(8645032, -1500, 5, False)
    sm.spawnMob(8645032, -1000, 5, False)
    sm.spawnMob(8645032, -500, 5, False)
    sm.spawnMob(8645032, 500, 5, False)
    sm.spawnMob(8645032, 1000, 5, False)
    sm.spawnMob(8645032, 1400, 5, False)
