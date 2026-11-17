# 302020600 Rescue Girl - Evidence in Ellin Forest (32635): Omens attack on the way out of Ellin Forest
if sm.hasQuest(32635) and not sm.isComplete(32635) and not sm.hasMobsInField():
    for x in (-1500, -1100, -700):
        sm.spawnMob(8230047, x, 90, False)
