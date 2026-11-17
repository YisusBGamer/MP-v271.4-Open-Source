# 450007510 Esfera: the mirror within the mirror. Mirrors in Mirrors (37871): Will's Reflection (8880312), in your own
# copy (quest/q37871s, npc/3003558); Will (3003559) takes the quest back once it falls.
if sm.hasQuest(37871) and not sm.isComplete(37871) and chr.getInstance() is not None \
        and not sm.hasMobsInField(450007510, 8880312):
    sm.spawnMob(8880312, -100, 281, False, 1000000000)
