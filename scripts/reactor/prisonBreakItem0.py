# Escape party quest, Aerial Prison: a box holding a Prison Key
PRISON_KEY = 4001528
pos = sm.getPosition(objectID)
sm.dropItem(PRISON_KEY, pos.getX(), pos.getY())
sm.removeReactor()
sm.dispose()
