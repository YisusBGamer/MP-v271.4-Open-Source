# Portal script pt_330002402.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (330002023, 2): (330002402, 0),  # Shinsoo International School / Dark Music Room -> 330002402 (map id in script name)
    (330002023, 3): (330002402, 0),  # Shinsoo International School / Dark Music Room -> 330002402 (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
