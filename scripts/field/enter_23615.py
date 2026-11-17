# Getting Caught?!, the Black Wings Goon (23615): the foe appears in your copy of the map
if sm.hasQuest(23615) and not sm.isComplete(23615) and not sm.hasMobsInField():
    sm.spawnMob(9300643, 900, 28, False)
