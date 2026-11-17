# The Pursuit, Charmed Eliza (23285): the foe appears in your copy of the map
if sm.hasQuest(23285) and not sm.isComplete(23285) and not sm.hasMobsInField():
    sm.spawnMob(9300649, 300, 83, False)
