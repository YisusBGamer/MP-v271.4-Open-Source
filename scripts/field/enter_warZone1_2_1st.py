# 401070300 Nameless Forest Battleground, stage 1. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400120 Specter Battle Hound, 2400122 Specter Miner, 2400121 Specter Tamer
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400120, -520, -208),
            (2400122, -384, -208),
            (2400121, -248, -208),
            (2400120, -112, -208),
            (2400122, 22, -208),
            (2400121, 158, -208),
            (2400120, 294, -208),
            (2400122, 430, -208),
            (2400121, -350, 152),
            (2400120, -146, 152),
            (2400122, 56, 152),
            (2400121, 260, 152),
            (2400120, 1000, 152),
            (2400122, 1145, 152),
            (2400121, 1290, 152),
            (2400120, -477, 92),
    ):
        sm.spawnMob(mob, x, y, False)
