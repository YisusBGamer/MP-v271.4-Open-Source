# 401071200 Heliseum Downtown Battleground, final stage. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400123 Specter Shieldbearer, 2400124 Guerrilla Specter, 2400125 Power Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400123, -4002, -385),
            (2400124, -3714, -385),
            (2400125, -3427, -385),
            (2400123, -3140, -385),
            (2400124, -4718, -163),
            (2400125, -4430, -163),
            (2400123, -4143, -163),
            (2400124, -3856, -163),
            (2400125, -3320, -163),
            (2400123, -3032, -163),
            (2400124, -2745, -163),
            (2400125, -2458, -163),
            (2400123, -3175, 176),
            (2400124, -2896, 176),
            (2400125, -2617, 176),
            (2400123, -2338, 176),
            (2400124, -4552, 176),
            (2400125, -4365, 176),
            (2400123, -4178, 176),
            (2400124, -3991, 176),
    ):
        sm.spawnMob(mob, x, y, False)
