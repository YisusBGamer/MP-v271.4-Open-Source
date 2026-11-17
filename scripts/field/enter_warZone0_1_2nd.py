# 400050200 East Border Battleground 2, stage 2 of 3. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400016 Faded Specter Shieldbearer, 2400018 Faded Warrior Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400016, -50),
            (2400018, 175),
            (2400016, 400),
            (2400018, 625),
            (2400016, 850),
            (2400018, 1075),
            (2400016, 1300),
            (2400018, 1525),
            (2400016, 1750),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
