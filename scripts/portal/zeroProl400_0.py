# Portal script zeroProl400_0.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (321000400, 2): (321000500, 1),  # Umbra Temple / Temple Entrance -> Umbra Temple / Dusty Corridor (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
