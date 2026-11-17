# 401072400 Twisted Forest Battleground, final stage. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400120 Specter Battle Hound, 2400121 Specter Tamer, 2400123 Specter Shieldbearer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400120, -575, 182),
            (2400121, -379, 182),
            (2400123, -183, 182),
            (2400120, 13, 182),
            (2400121, 209, 182),
            (2400123, 405, 182),
            (2400120, 601, 182),
            (2400121, 797, 182),
            (2400123, 993, 182),
            (2400120, 1189, 182),
            (2400121, 1385, 182),
            (2400123, -25, -478),
            (2400120, 250, -478),
            (2400121, 525, -478),
            (2400123, -475, -118),
            (2400120, -225, -118),
            (2400121, 25, -118),
            (2400123, 1055, -178),
            (2400120, 1375, -178),
            (2400121, -700, 122),
    ):
        sm.spawnMob(mob, x, y, False)
