# Portal script go_867202100.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867202110, 1): (867202100, 0),  # Abrup Basin / Rolling Caravan -> Abrup Basin / Rolling Caravan (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
