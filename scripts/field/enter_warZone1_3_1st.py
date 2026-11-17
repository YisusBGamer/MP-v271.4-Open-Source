# 401070600 Heliseum Downtown Battleground, stage 1. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400122 Specter Miner, 2400121 Specter Tamer, 2400124 Guerrilla Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400122, 3435, 188),
            (2400121, 3725, 188),
            (2400124, 4015, 188),
            (2400122, 4305, 188),
            (2400121, 2598, -510),
            (2400124, 3029, -510),
            (2400122, 3460, -510),
            (2400121, 3237, -261),
            (2400124, 3668, -261),
            (2400122, 4099, -261),
            (2400121, 2167, -149),
            (2400124, 2597, -149),
            (2400122, 3028, -149),
            (2400121, 2149, 188),
            (2400124, 2425, 188),
            (2400122, 2701, 188),
    ):
        sm.spawnMob(mob, x, y, False)
