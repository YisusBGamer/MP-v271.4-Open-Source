# 401071900 Tyrant's Castle Battleground, stage 1. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400126 Specter Engineer, 2400127 Warrior Specter, 2400128 Magician Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400126, -5118, 153),
            (2400127, -4894, 153),
            (2400128, -4671, 153),
            (2400126, -4447, 153),
            (2400127, -4224, 153),
            (2400128, -4001, 153),
            (2400126, -3777, 153),
            (2400127, -3554, 153),
            (2400128, -3331, 153),
            (2400126, -3107, 153),
            (2400127, -2884, 153),
            (2400128, -2661, 153),
            (2400126, -3850, -238),
            (2400127, -3602, -238),
            (2400128, -3355, -238),
            (2400126, -5225, -239),
            (2400127, -4977, -239),
            (2400128, -4730, -239),
    ):
        sm.spawnMob(mob, x, y, False)
