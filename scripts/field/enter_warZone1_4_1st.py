# 401071000 Heliseum Downtown Battleground, stage 1. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400121 Specter Tamer, 2400123 Specter Shieldbearer, 2400124 Guerrilla Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400121, 3435, 188),
            (2400123, 3725, 188),
            (2400124, 4015, 188),
            (2400121, 4305, 188),
            (2400123, 2598, -510),
            (2400124, 3029, -510),
            (2400121, 3460, -510),
            (2400123, 3237, -261),
            (2400124, 3668, -261),
            (2400121, 4099, -261),
            (2400123, 2167, -149),
            (2400124, 2597, -149),
            (2400121, 3028, -149),
            (2400123, 2149, 188),
            (2400124, 2425, 188),
            (2400121, 2701, 188),
    ):
        sm.spawnMob(mob, x, y, False)
