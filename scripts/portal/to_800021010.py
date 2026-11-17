# Portal script to_800021010.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800021102, 1): (800021010, 1),  # Oni Forest / Beginning of the Perilous Path -> Oni Forest / Perilous Path (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
