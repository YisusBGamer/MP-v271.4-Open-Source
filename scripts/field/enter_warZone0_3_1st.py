# 400050600 West Border Battleground 1, stage 1 of 2. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400019 Faded Magician Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400019, 1300),
            (2400019, 1521),
            (2400019, 1742),
            (2400019, 1963),
            (2400019, 2184),
            (2400019, 2405),
            (2400019, 2626),
            (2400019, 2847),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
