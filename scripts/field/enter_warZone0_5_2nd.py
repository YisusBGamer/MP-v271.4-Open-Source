# 400051300 South Border Battleground 2, stage 2 of 3. The exit (portal/finish_warZone0.py) opens once every Specter is beaten.
# Pantheon battlefield quests and their battlegrounds: portal/finish_warZone0.py.
sm.showFieldEffect("monsterPark/stageEff/stage", 0)
sm.showFieldEffect("monsterPark/stageEff/number/2", 0)
# 2400018 Faded Warrior Specter, 2400019 Faded Magician Specter, 2400020 Faded Reaper Specter
if not sm.hasMobsInField():
    for mob, x in (
            (2400018, 250),
            (2400019, 427),
            (2400020, 604),
            (2400018, 781),
            (2400019, 958),
            (2400020, 1135),
            (2400018, 1312),
            (2400019, 1489),
            (2400020, 1666),
            (2400018, 1843),
    ):
        sm.spawnMob(mob, x, 29, False)
sm.dispose()
