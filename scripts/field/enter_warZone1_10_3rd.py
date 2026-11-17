# 401072700 Twisted Forest Battleground, final stage. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400121 Specter Tamer, 2400122 Specter Miner, 2400124 Guerrilla Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400121, -575, 182),
            (2400122, -379, 182),
            (2400124, -183, 182),
            (2400121, 13, 182),
            (2400122, 209, 182),
            (2400124, 405, 182),
            (2400121, 601, 182),
            (2400122, 797, 182),
            (2400124, 993, 182),
            (2400121, 1189, 182),
            (2400122, 1385, 182),
            (2400124, -25, -478),
            (2400121, 250, -478),
            (2400122, 525, -478),
            (2400124, -475, -118),
            (2400121, -225, -118),
            (2400122, 25, -118),
            (2400124, 1055, -178),
            (2400121, 1375, -178),
            (2400122, -700, 122),
    ):
        sm.spawnMob(mob, x, y, False)
