# Hongbu's Roof (reactor 2221003): "Drop Hongbu's Seed to summon the Gourd." (the reactor's own description)
# Hongbu's Seed (4031244) dropped on the roof (ReactorItemEvents) grows into a Gourd (9500400); breaking it gives
# what's inside - for "Opening Hongbu's Gourd" (3608) the Box of Jewelry (4031223) (V119_Gourd_Drops.sql).
GOURD = 9500400

if reactor.getState() >= 1:
    pos = sm.getPosition(objectID)
    sm.spawnMob(GOURD, pos.getX(), pos.getY(), False)
    sm.chatScript("The seed sprouts, and a big gourd grows on the roof!")
    # ready for the next seed
    reactor.setState(0)
    reactor.broadcastChangeState()
sm.dispose()
