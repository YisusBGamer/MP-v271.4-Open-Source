# 400051200 South Border Battleground 2, stage 1 of 3. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/1", 0)
# 2400018 Faded Warrior Specter, 2400019 Faded Magician Specter, 2400020 Faded Reaper Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400018, 250),
            (2400019, 478),
            (2400020, 706),
            (2400018, 934),
            (2400019, 1162),
            (2400020, 1390),
            (2400018, 1618),
            (2400019, 1846),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
