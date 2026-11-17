# Portal script go_867139400.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867139000, 10): (867139400, 0),  # Celestial Festival / Festival Venue -> Celestial Festival / Sanctuary of Stars (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
