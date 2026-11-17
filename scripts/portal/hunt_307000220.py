# Portal script hunt_307000220.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (307000220, 3): (307000221, 1),  # Shadowvale / Field 2-B-1 -> Shadowvale / Field 2-B-2 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
