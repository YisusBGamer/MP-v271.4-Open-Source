# Surl's Water Tank (reactor 3109000) | Edelstein: Surl's Water Cellar (931000410)
# Runs each time a dropped bottle of water (4000601) fills the tank one step (ReactorItemEvents, from the
# reactor's Reactor.wz events: one bottle at a time, 10 to fill it; it drains back if nothing comes for a while).
# "Catching the Water Thief Monster" (23120) waits for the record of quest 23130 to be "1".
TANK_FULL = 10

state = reactor.getState()
if state >= TANK_FULL:
    if sm.hasQuest(23120):
        sm.setQRValue(23130, "1", False)
        sm.chatScript("The water tank is full! Go back to Surl.")
    # empty again for the next player (the cellar is shared)
    reactor.setState(0)
    reactor.broadcastChangeState()
else:
    sm.chatScript("The water tank fills up a little. (%d/%d)" % (state, TANK_FULL))
sm.dispose()
