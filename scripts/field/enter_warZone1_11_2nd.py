# 401072900 Heliseum Downtown Battleground, stage 2. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400124 Guerrilla Specter, 2400125 Power Specter, 2400126 Specter Engineer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400124, -603, -229),
            (2400125, -434, -229),
            (2400126, -265, -229),
            (2400124, -96, -229),
            (2400125, 73, -229),
            (2400126, 828, -387),
            (2400124, 1073, -387),
            (2400125, 1318, -387),
            (2400126, -207, 93),
            (2400124, 12, 93),
            (2400125, 232, 93),
            (2400126, 1144, 177),
            (2400124, 1354, 177),
            (2400125, 1564, 177),
            (2400126, -976, 177),
            (2400124, -889, 177),
    ):
        sm.spawnMob(mob, x, y, False)
