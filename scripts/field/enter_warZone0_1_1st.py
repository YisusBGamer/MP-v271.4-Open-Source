# 400050100 East Border Battleground 2, stage 1 of 3. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400016 Faded Specter Shieldbearer, 2400018 Faded Warrior Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400016, -50),
            (2400018, 207),
            (2400016, 464),
            (2400018, 721),
            (2400016, 978),
            (2400018, 1235),
            (2400016, 1492),
            (2400018, 1749),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
