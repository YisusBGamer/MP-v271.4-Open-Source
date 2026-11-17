# Portal script to_800021106.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800021105, 1): (800021106, 0),  # Oni Forest / End of the Perilous Path -> Oni Forest / Oni Commons (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
