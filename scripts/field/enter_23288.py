# Stopping the Conspiracy, the robed figure (23288): the foe appears in your copy of the map
if sm.hasQuest(23288) and not sm.isComplete(23288) and not sm.hasMobsInField():
    sm.spawnMob(9300647, -900, 162, False)
