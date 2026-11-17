# 401071700 Heliseum Downtown Battleground, stage 2. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400124 Guerrilla Specter, 2400125 Power Specter, 2400126 Specter Engineer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400124, -606, -229),
            (2400125, -470, -229),
            (2400126, -335, -229),
            (2400124, -200, -229),
            (2400125, -65, -229),
            (2400126, 70, -229),
            (2400124, 828, -387),
            (2400125, 991, -387),
            (2400126, 1154, -387),
            (2400124, 1318, -387),
            (2400125, -207, 93),
            (2400126, 12, 93),
            (2400124, 232, 93),
            (2400125, 1144, 177),
            (2400126, 1354, 177),
            (2400124, 1564, 177),
            (2400125, -976, 177),
            (2400126, -889, 177),
    ):
        sm.spawnMob(mob, x, y, False)
