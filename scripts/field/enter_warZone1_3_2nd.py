# 401070700 Heliseum Downtown Battleground, stage 2. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400122 Specter Miner, 2400121 Specter Tamer, 2400124 Guerrilla Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400122, -626, -229),
            (2400121, -457, -229),
            (2400124, -288, -229),
            (2400122, -119, -229),
            (2400121, 50, -229),
            (2400124, 828, -387),
            (2400122, 1073, -387),
            (2400121, 1318, -387),
            (2400124, -207, 93),
            (2400122, 12, 93),
            (2400121, 232, 93),
            (2400124, 1144, 177),
            (2400122, 1354, 177),
            (2400121, 1564, 177),
            (2400124, -976, 177),
            (2400122, -889, 177),
    ):
        sm.spawnMob(mob, x, y, False)
