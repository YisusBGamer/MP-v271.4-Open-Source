# 401070500 Nameless Forest Battleground, final stage. The exit (finish_warZone1) opens once every Specter is beaten.
# Heliseum battlefield quests and their stages: portal/finish_warZone1.py.
sm.showFieldEffect("monsterPark/stageEff/final", 0)
# 2400121 Specter Tamer, 2400122 Specter Miner, 2400124 Guerrilla Specter
if not sm.hasMobsInField():
    for mob, x, y in (
            (2400121, -520, 152),
            (2400122, -318, 152),
            (2400124, -117, 152),
            (2400121, 83, 152),
            (2400122, 284, 152),
            (2400124, 485, 152),
            (2400121, 686, 152),
            (2400122, 887, 152),
            (2400124, 1088, 152),
            (2400121, 1290, 152),
            (2400122, -160, -148),
            (2400124, 66, -148),
            (2400121, 292, -148),
            (2400122, 518, -148),
            (2400124, 744, -148),
            (2400121, 970, -148),
            (2400122, 20, -508),
            (2400124, 276, -508),
            (2400121, 533, -508),
            (2400122, 790, -508),
    ):
        sm.spawnMob(mob, x, y, False)
