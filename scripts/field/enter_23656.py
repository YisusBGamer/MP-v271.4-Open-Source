# Invasion, Beryl (23656): the foe appears in your copy of the map
if sm.hasQuest(23656) and not sm.isComplete(23656) and not sm.hasMobsInField():
    sm.spawnMob(9300637, 1200, 29, False)
