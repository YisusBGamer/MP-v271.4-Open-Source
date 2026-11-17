# 401072100 Tyrant's Castle Battleground, final stage. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400127 Warrior Specter, 2400128 Magician Specter, 2400130 Warrior Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400127, -5770, 152),
            (2400128, -5613, 152),
            (2400130, -5456, 152),
            (2400127, -5299, 152),
            (2400128, -5142, 152),
            (2400130, -4985, 152),
            (2400127, -4828, 152),
            (2400128, -4671, 152),
            (2400130, -4514, 152),
            (2400127, -4357, 152),
            (2400128, -4200, 152),
            (2400130, -4043, 152),
            (2400127, -5537, -238),
            (2400128, -5372, -238),
            (2400130, -5207, -238),
            (2400127, -5042, -238),
            (2400128, -4685, -238),
            (2400130, -4520, -238),
            (2400127, -4355, -238),
            (2400128, -4190, -238),
    ):
        sm.spawnMob(mob, x, y, False)
