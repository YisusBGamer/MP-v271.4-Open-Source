# Portal script west_350120300.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350120400, 1): (350120300, 0),  # Heroes of Maple / Ruins Entry -> Heroes of Maple / Ruins Entry (map id in script name)
    (350120500, 1): (350120300, 0),  # Heroes of Maple / Ruins Entry -> Heroes of Maple / Ruins Entry (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
