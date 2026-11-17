# Portal script ptup03.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (303020300, 2): (303020000, 1),  # Aqua Road / Onyx Ocean -> Aqua Road / Pearl Ocean (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
