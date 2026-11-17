# 401072800 Heliseum Downtown Battleground, stage 1. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400124 Guerrilla Specter, 2400125 Power Specter, 2400126 Specter Engineer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400124, 3051, -261),
            (2400125, 3400, -261),
            (2400126, 3749, -261),
            (2400124, 4099, -261),
            (2400125, 3435, 188),
            (2400126, 3870, 188),
            (2400124, 4305, 188),
            (2400125, 2598, -510),
            (2400126, 3029, -510),
            (2400124, 3460, -510),
            (2400125, 2167, -149),
            (2400126, 2597, -149),
            (2400124, 3028, -149),
            (2400125, 2149, 188),
            (2400126, 2425, 188),
            (2400124, 2701, 188),
    ):
        sm.spawnMob(mob, x, y, False)
