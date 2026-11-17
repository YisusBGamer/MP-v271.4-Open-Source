# Portal script ramuramu_out.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (160070000, 2): (160070001, 2),  # Ramuramu Valley / Ramuramu Hot Springs -> Ramuramu Valley / Path to the Ruins (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
