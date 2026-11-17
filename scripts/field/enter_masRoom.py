# 931050110 - Field for demon advancements

if sm.hasQuest(23221): # Demon 4th job
    sm.spawnMob(9001058, 671, -14, False)
elif sm.hasQuest(23218) and not sm.isComplete(23218) and not sm.hasMobsInField(): # Sparring with Mastema (Demon story)
    sm.spawnMob(9001037, 671, -14, False)
