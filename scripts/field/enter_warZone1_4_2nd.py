# 401071100 Heliseum Downtown Battleground, stage 2. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400121 Specter Tamer, 2400123 Specter Shieldbearer, 2400124 Guerrilla Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400121, -600, -229),
            (2400123, -467, -229),
            (2400124, -335, -229),
            (2400121, -203, -229),
            (2400123, -71, -229),
            (2400124, 61, -229),
            (2400121, 828, -387),
            (2400123, 991, -387),
            (2400124, 1154, -387),
            (2400121, 1318, -387),
            (2400123, -207, 93),
            (2400124, 12, 93),
            (2400121, 232, 93),
            (2400123, 1144, 177),
            (2400124, 1354, 177),
            (2400121, 1564, 177),
            (2400123, -976, 177),
            (2400124, -889, 177),
    ):
        sm.spawnMob(mob, x, y, False)
