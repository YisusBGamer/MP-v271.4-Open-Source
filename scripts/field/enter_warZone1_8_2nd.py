# 401072000 Tyrant's Castle Battleground, stage 2. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400127 Warrior Specter, 2400128 Magician Specter, 2400130 Warrior Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400127, -5117, 153),
            (2400128, -4994, 153),
            (2400130, -4871, 153),
            (2400127, -4748, 153),
            (2400128, -4626, 153),
            (2400130, -4503, 153),
            (2400127, -4380, 153),
            (2400128, -4258, 153),
            (2400130, -4135, 153),
            (2400127, -4012, 153),
            (2400128, -3890, 153),
            (2400130, -3767, 153),
            (2400127, -3644, 153),
            (2400128, -3522, 153),
            (2400130, -5132, -214),
            (2400127, -4967, -214),
            (2400128, -4802, -214),
            (2400130, -4637, -214),
    ):
        sm.spawnMob(mob, x, y, False)
