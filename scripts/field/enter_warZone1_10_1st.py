# 401072500 Twisted Forest Battleground, stage 1. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400120 Specter Battle Hound, 2400121 Specter Tamer, 2400122 Specter Miner
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400120, -575, 182),
            (2400121, -435, 182),
            (2400122, -295, 182),
            (2400120, -155, 182),
            (2400121, -15, 182),
            (2400122, 125, 182),
            (2400120, 265, 182),
            (2400121, 405, 182),
            (2400122, 545, 182),
            (2400120, 685, 182),
            (2400121, 825, 182),
            (2400122, 965, 182),
            (2400120, 1105, 182),
            (2400121, 1245, 182),
            (2400122, 1385, 182),
            (2400120, -700, 122),
    ):
        sm.spawnMob(mob, x, y, False)
