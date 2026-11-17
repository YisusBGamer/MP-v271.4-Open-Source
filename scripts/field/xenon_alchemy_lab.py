# 926160001 Alchemy Lab (see CustomFieldScripts) - Encounter, Beryl (23708): the foe appears in your copy of the map
if sm.hasQuest(23708) and not sm.isComplete(23708) and not sm.hasMobsInField():
    sm.spawnMob(9300638, 300, 27, False)
