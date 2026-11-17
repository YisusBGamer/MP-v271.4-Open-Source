# Portal script 867120400_out.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867120500, 2): (867120400, 0),  # Cave of Life / The Cave of Trial -> Cave of Life / The Cave of Trial (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
