# 401071400 Foggy Valley Battleground, stage 1. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400121 Specter Tamer, 2400124 Guerrilla Specter, 2400125 Power Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400121, -266, 182),
            (2400124, -158, 182),
            (2400125, -50, 182),
            (2400121, 57, 182),
            (2400124, 165, 182),
            (2400125, 273, 182),
            (2400121, 381, 182),
            (2400124, 489, 182),
            (2400125, 597, 182),
            (2400121, 705, 182),
            (2400124, -205, -58),
            (2400125, -51, -58),
            (2400121, 103, -58),
            (2400124, 257, -58),
            (2400125, 411, -58),
            (2400121, 565, -58),
    ):
        sm.spawnMob(mob, x, y, False)
