# 400050400 North Border Battleground 1, stage 1 of 2. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400017 Faded Guerrilla Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400017, 1150),
            (2400017, 1364),
            (2400017, 1578),
            (2400017, 1792),
            (2400017, 2006),
            (2400017, 2220),
            (2400017, 2434),
            (2400017, 2648),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
