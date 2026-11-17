# Pile of Rocks (reactor 2221000), Korean Folk Town: "Drop Roasted Pork to summon the Yellow King Goblin." The pork
# dropped on it (ReactorItemEvents) moves it to state 1.
YELLOW_KING_GOBLIN = 5100108

if reactor.getState() >= 1:
    pos = sm.getPosition(objectID)
    sm.spawnMob(YELLOW_KING_GOBLIN, pos.getX(), pos.getY(), False)
    reactor.setState(0)
    reactor.broadcastChangeState()
sm.dispose()
