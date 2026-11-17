# 400050700 West Border Battleground 1, stage 2 of 2. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400019 Faded Magician Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400019, 1300),
            (2400019, 1472),
            (2400019, 1644),
            (2400019, 1816),
            (2400019, 1988),
            (2400019, 2160),
            (2400019, 2332),
            (2400019, 2504),
            (2400019, 2676),
            (2400019, 2848),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
