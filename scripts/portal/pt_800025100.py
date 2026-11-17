# Portal script pt_800025100.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800025100, 1): (800025002, 0),  # Zipangu / Forest of Oblivion -> Zipangu / Forest of Oblivion (exit to the map's return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
