# Portal script zeroProl300_0.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (321000300, 2): (321000400, 1),  # Shadowvale / Field 1-B -> Umbra Temple / Temple Entrance (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
