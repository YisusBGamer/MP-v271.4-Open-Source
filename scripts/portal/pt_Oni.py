# Portal script pt_Oni.
# Spiritwilds Clearing -> the Oni Forest (Mushroom Shrine Tales).
#   A Meeting with Kamaitachi (58732): the Oni Commons, where all three kinds of Oni attack (your own copy).
#   Daidarabotchi's Second Message through Collecting the Metal Element (58730-58735): the Beginning of the Perilous Path.
ONI = (9400031, 9400032)  # the Commons already has the Suspicious Oni (9400033)
if sm.hasQuest(58732):
    sm.warpInstanceIn(800021030, 1)
    field = chr.getField()
    if field.getId() == 800021030:
        for i in range(10):
            field.spawnMob(ONI[i % 2], -400 + i * 250, 35, True, 0)
elif any(sm.hasQuest(q) for q in (58730, 58731, 58733, 58734, 58735)):
    sm.warp(800021102, 1)
else:
    sm.warp(800021101, 1)
sm.dispose()
