# Portal script pt_307000000.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (307000000, 1): (307000010, 1),  # Shadowvale / Field 1-A-1 -> Shadowvale / Field 1-A-2 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
