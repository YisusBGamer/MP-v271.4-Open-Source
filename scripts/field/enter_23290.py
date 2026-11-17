# A Strange Letter, the letter's author (23290): the foe appears in your copy of the map
if sm.hasQuest(23290) and not sm.isComplete(23290) and not sm.hasMobsInField():
    sm.spawnMob(9300648, 200, 452, False)
