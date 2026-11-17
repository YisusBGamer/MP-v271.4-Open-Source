# 400050300 East Border Battleground 2, stage 3 of 3. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/3", 0)
# 2400016 Faded Specter Shieldbearer, 2400018 Faded Warrior Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400016, -50),
            (2400018, 150),
            (2400016, 350),
            (2400018, 550),
            (2400016, 750),
            (2400018, 950),
            (2400016, 1150),
            (2400018, 1350),
            (2400016, 1550),
            (2400018, 1750),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
