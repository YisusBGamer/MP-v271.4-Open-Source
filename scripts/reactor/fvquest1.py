# Nolbu's Roof (reactor 2221004): "Drop Nolbu's Seed to summon the Gourd." (the reactor's own description)
# Nolbu's Seed (4031245) dropped on the roof (ReactorItemEvents) grows into a Gourd (9500400); breaking it gives
# what's inside - for "Opening Nolbu's Gourd" (3607) the Warrant of Attachment (4031224) (V119_Gourd_Drops.sql).
GOURD = 9500400

if reactor.getState() >= 1:
    pos = sm.getPosition(objectID)
    sm.spawnMob(GOURD, pos.getX(), pos.getY(), False)
    sm.chatScript("The seed sprouts, and a big gourd grows on the roof!")
    # ready for the next seed
    reactor.setState(0)
    reactor.broadcastChangeState()
sm.dispose()
