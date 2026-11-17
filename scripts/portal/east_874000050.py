# Portal script east_874000050.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (874000050, 3): (874000051, 1),  # Zipangu / Momijigaoka -> Zipangu / Momijigaoka (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
