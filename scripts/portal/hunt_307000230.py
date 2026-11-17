# Portal script hunt_307000230.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (307000230, 2): (307000231, 1),  # Shadowvale / Field 2-C-1 -> Shadowvale / Field 2-C-2 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
