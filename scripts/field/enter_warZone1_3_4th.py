# 401070900 Foggy Valley Battleground, final stage. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400121 Specter Tamer, 2400124 Guerrilla Specter, 2400126 Specter Engineer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400121, -266, 182),
            (2400124, -191, 182),
            (2400126, -116, 182),
            (2400121, -41, 182),
            (2400124, 32, 182),
            (2400126, 107, 182),
            (2400121, 182, 182),
            (2400124, 256, 182),
            (2400126, 331, 182),
            (2400121, 406, 182),
            (2400124, 480, 182),
            (2400126, 555, 182),
            (2400121, 630, 182),
            (2400124, 705, 182),
            (2400126, 380, -28),
            (2400121, 486, -28),
            (2400124, 593, -28),
            (2400126, 700, -28),
    ):
        sm.spawnMob(mob, x, y, False)
