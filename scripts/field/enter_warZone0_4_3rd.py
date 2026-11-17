# 400051000 South Border Battleground 1, stage 3 of 4. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/3", 0)
# 2400015 Faded Specter Battle Hound, 2400016 Faded Specter Shieldbearer, 2400017 Faded Guerrilla Specter, 2400018 Faded Warrior Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400015, 250),
            (2400016, 427),
            (2400017, 604),
            (2400018, 781),
            (2400015, 958),
            (2400016, 1135),
            (2400017, 1312),
            (2400018, 1489),
            (2400015, 1666),
            (2400016, 1843),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
