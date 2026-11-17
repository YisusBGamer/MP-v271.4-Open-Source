# 401071300 Foggy Valley Battleground. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400124 Guerrilla Specter, 2400125 Power Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400124, -266, 182),
            (2400125, -168, 182),
            (2400124, -71, 182),
            (2400125, 25, 182),
            (2400124, 122, 182),
            (2400125, 219, 182),
            (2400124, 316, 182),
            (2400125, 413, 182),
            (2400124, 510, 182),
            (2400125, 607, 182),
            (2400124, 705, 182),
            (2400125, -205, -58),
            (2400124, -76, -58),
            (2400125, 51, -58),
            (2400124, 180, -58),
            (2400125, 308, -58),
            (2400124, 436, -58),
            (2400125, 565, -58),
    ):
        sm.spawnMob(mob, x, y, False)
