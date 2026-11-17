# 401073300 Foggy Valley Battleground, final stage. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400124 Guerrilla Specter, 2400125 Power Specter, 2400126 Specter Engineer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400124, 825, 182),
            (2400125, 951, 182),
            (2400126, 1078, 182),
            (2400124, 1205, 182),
            (2400125, 1331, 182),
            (2400126, 1458, 182),
            (2400124, 1585, 182),
            (2400125, 1711, 182),
            (2400126, 1838, 182),
            (2400124, 1965, 182),
            (2400125, 1197, -598),
            (2400126, 1286, -598),
            (2400124, 1375, -598),
            (2400125, 1415, -418),
            (2400126, 1620, -418),
            (2400124, 1825, -418),
            (2400125, 1045, -298),
            (2400126, 1285, -298),
            (2400124, 1415, -58),
            (2400125, 1645, -58),
    ):
        sm.spawnMob(mob, x, y, False)
