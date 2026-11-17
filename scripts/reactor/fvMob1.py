# Stone Lantern (reactor 2221001), Korean Folk Town: "Drop Rice Wine to summon the Blue King Goblin." The wine dropped
# on it (ReactorItemEvents) moves it to state 1.
BLUE_KING_GOBLIN = 5100106

if reactor.getState() >= 1:
    pos = sm.getPosition(objectID)
    sm.spawnMob(BLUE_KING_GOBLIN, pos.getX(), pos.getY(), False)
    reactor.setState(0)
    reactor.broadcastChangeState()
sm.dispose()
