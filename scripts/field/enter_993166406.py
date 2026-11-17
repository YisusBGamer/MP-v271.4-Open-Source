# Knights Test Site 2 (993166406): Kiku's tests. Physical Training (36456) needs 3 of them defeated, Combat Technique
# (36458) 3 Tino Feathers, which they drop (mob_drops, V190).
MOB = 8645270
FEATHER = 4036882
SPOTS = (-330, -230, 160)

if sm.hasQuest(36456) and not sm.isComplete(36456):
    for x in SPOTS:
        sm.spawnMob(MOB, x, 80, False)
    sm.chatScript("Defeat the monsters to finish Kiku's test.")
elif sm.hasQuest(36458) and sm.getQuantityOfItem(FEATHER) < 3:
    for x in SPOTS:
        sm.spawnMob(MOB, x, 80, False)
    sm.chatScript("Use your skills on the monsters and gather 3 Tino Feathers.")
