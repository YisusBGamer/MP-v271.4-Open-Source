# 401073200 Foggy Valley Battleground, stage 1. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400124 Guerrilla Specter, 2400125 Power Specter, 2400126 Specter Engineer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400124, 825, 182),
            (2400125, 967, 182),
            (2400126, 1110, 182),
            (2400124, 1252, 182),
            (2400125, 1395, 182),
            (2400126, 1537, 182),
            (2400124, 1680, 182),
            (2400125, 1822, 182),
            (2400126, 1965, 182),
            (2400124, 1415, -418),
            (2400125, 1555, -418),
            (2400126, 1695, -418),
            (2400124, 1455, -58),
            (2400125, 1645, -58),
            (2400126, 1095, -298),
            (2400124, 1285, -298),
    ):
        sm.spawnMob(mob, x, y, False)
