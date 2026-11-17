# 400051500 North Border Battleground 2, stage 1 of 3. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400017 Faded Guerrilla Specter, 2400016 Faded Specter Shieldbearer, 2400018 Faded Warrior Specter, 2400020 Faded Reaper Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400017, 1150),
            (2400016, 1364),
            (2400018, 1578),
            (2400020, 1792),
            (2400017, 2006),
            (2400016, 2220),
            (2400018, 2434),
            (2400020, 2648),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
