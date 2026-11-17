# 400050500 North Border Battleground 1, stage 2 of 2. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400017 Faded Guerrilla Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400017, 1150),
            (2400017, 1316),
            (2400017, 1482),
            (2400017, 1648),
            (2400017, 1814),
            (2400017, 1980),
            (2400017, 2146),
            (2400017, 2312),
            (2400017, 2478),
            (2400017, 2644),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
