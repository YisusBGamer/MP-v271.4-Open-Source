# 401072600 Twisted Forest Battleground, stage 2. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400120 Specter Battle Hound, 2400121 Specter Tamer, 2400122 Specter Miner
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400120, -575, 182),
            (2400121, -330, 182),
            (2400122, -85, 182),
            (2400120, 160, 182),
            (2400121, 405, 182),
            (2400122, 650, 182),
            (2400120, 895, 182),
            (2400121, 1140, 182),
            (2400122, 1385, 182),
            (2400120, -385, -238),
            (2400121, 0, -238),
            (2400122, 385, -238),
            (2400120, 515, -118),
            (2400121, 900, -118),
            (2400122, 1285, -118),
            (2400120, 695, -538),
            (2400121, 1105, -538),
            (2400122, -700, 122),
    ):
        sm.spawnMob(mob, x, y, False)
