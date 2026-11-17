# 401073100 Foggy Valley Battleground, final stage. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400125 Power Specter, 2400126 Specter Engineer, 2400128 Magician Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400125, -266, 182),
            (2400126, -168, 182),
            (2400128, -71, 182),
            (2400125, 25, 182),
            (2400126, 122, 182),
            (2400128, 219, 182),
            (2400125, 316, 182),
            (2400126, 413, 182),
            (2400128, 510, 182),
            (2400125, 607, 182),
            (2400126, 705, 182),
            (2400128, -205, -58),
            (2400125, -76, -58),
            (2400126, 51, -58),
            (2400128, 180, -58),
            (2400125, 308, -58),
            (2400126, 436, -58),
            (2400128, 565, -58),
    ):
        sm.spawnMob(mob, x, y, False)
