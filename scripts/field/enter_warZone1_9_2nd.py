# 401072300 Twisted Forest Battleground, stage 2. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400120 Specter Battle Hound, 2400123 Specter Shieldbearer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400120, -575, 182),
            (2400123, -330, 182),
            (2400120, -85, 182),
            (2400123, 160, 182),
            (2400120, 405, 182),
            (2400123, 650, 182),
            (2400120, 895, 182),
            (2400123, 1140, 182),
            (2400120, 1385, 182),
            (2400123, -385, -238),
            (2400120, 0, -238),
            (2400123, 385, -238),
            (2400120, 515, -118),
            (2400123, 900, -118),
            (2400120, 1285, -118),
            (2400123, 695, -538),
            (2400120, 1105, -538),
            (2400123, -700, 122),
    ):
        sm.spawnMob(mob, x, y, False)
