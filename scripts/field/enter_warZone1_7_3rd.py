# 401071800 Heliseum Downtown Battleground, final stage. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400125 Power Specter, 2400126 Specter Engineer, 2400128 Magician Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400125, -4002, -385),
            (2400126, -3714, -385),
            (2400128, -3427, -385),
            (2400125, -3140, -385),
            (2400126, -4718, -163),
            (2400128, -4430, -163),
            (2400125, -4143, -163),
            (2400126, -3856, -163),
            (2400128, -3320, -163),
            (2400125, -3032, -163),
            (2400126, -2745, -163),
            (2400128, -2458, -163),
            (2400125, -3175, 176),
            (2400126, -2896, 176),
            (2400128, -2617, 176),
            (2400125, -2338, 176),
            (2400126, -4552, 176),
            (2400128, -4365, 176),
            (2400125, -4178, 176),
            (2400126, -3991, 176),
    ):
        sm.spawnMob(mob, x, y, False)
