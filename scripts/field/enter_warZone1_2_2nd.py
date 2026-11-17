# 401070400 Nameless Forest Battleground, stage 2. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400120 Specter Battle Hound, 2400122 Specter Miner, 2400121 Specter Tamer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400120, -350, 152),
            (2400122, -145, 152),
            (2400121, 60, 152),
            (2400120, 265, 152),
            (2400122, 470, 152),
            (2400121, 675, 152),
            (2400120, 880, 152),
            (2400122, 1085, 152),
            (2400121, 1290, 152),
            (2400120, -250, -208),
            (2400122, 6, -208),
            (2400121, 263, -208),
            (2400120, 520, -208),
            (2400122, 650, -388),
            (2400121, 1060, -388),
            (2400120, 650, -88),
            (2400122, 1060, -88),
            (2400121, -477, 92),
    ):
        sm.spawnMob(mob, x, y, False)
