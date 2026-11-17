# 925040100 Sealed Temple (see CustomFieldScripts) - Catch the Shadow Knight! (21747, Aran)
if sm.hasQuest(21747) and not sm.isComplete(21747) and not sm.hasMobsInField():
    sm.spawnMob(9300351, 700, 51, False)
