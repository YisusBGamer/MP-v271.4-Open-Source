# 302040500 Omen's Call - Arin About (32644): Omens have Arin surrounded
if sm.hasQuest(32644) and not sm.isComplete(32644) and not sm.hasMobsInField():
    for x in (-950, -800, -650, -500, -350, -200, -50, 100, 250, 400, 550, 650):
        sm.spawnMob(8230047, x, 185, False)
    for x in (-750, -500, -250):
        sm.spawnMob(8230047, x, -115, False)
