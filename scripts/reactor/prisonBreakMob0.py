# Escape party quest, Aerial Prison: a box that releases Prison Guard Boars
PRISON_GUARD_BOAR = 9300452
pos = sm.getPosition(objectID)
for i in range(3):
    sm.spawnMob(PRISON_GUARD_BOAR, pos.getX(), pos.getY(), False)
sm.removeReactor()
sm.dispose()
