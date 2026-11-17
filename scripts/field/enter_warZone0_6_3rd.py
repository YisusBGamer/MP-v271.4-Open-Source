# 400051700 North Border Battleground 2, stage 3 of 3. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/3", 0)
# 2400017 Faded Guerrilla Specter, 2400016 Faded Specter Shieldbearer, 2400018 Faded Warrior Specter, 2400020 Faded Reaper Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400017, 1150),
            (2400016, 1316),
            (2400018, 1482),
            (2400020, 1648),
            (2400017, 1814),
            (2400016, 1980),
            (2400018, 2146),
            (2400020, 2312),
            (2400017, 2478),
            (2400016, 2644),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
