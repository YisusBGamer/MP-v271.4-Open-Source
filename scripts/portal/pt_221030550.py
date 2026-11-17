# Portal script pt_221030550.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (221030551, 4): (221030550, 0),  # Inside the Mothership / Corridor 105 -> Inside the Mothership / Corridor 105 (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
