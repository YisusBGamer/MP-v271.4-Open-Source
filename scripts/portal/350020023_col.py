# Portal script 350020023_col.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350020023, 2): (350020000, 2),  # Ereve / Boarding Area -> Ereve / Ereve Boarding Area (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
