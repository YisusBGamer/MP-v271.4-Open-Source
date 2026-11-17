# 400050900 South Border Battleground 1, stage 2 of 4. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400015 Faded Specter Battle Hound, 2400016 Faded Specter Shieldbearer, 2400017 Faded Guerrilla Specter, 2400018 Faded Warrior Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400015, 250),
            (2400016, 478),
            (2400017, 706),
            (2400018, 934),
            (2400015, 1162),
            (2400016, 1390),
            (2400017, 1618),
            (2400018, 1846),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
