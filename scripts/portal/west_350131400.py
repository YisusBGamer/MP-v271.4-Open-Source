# Portal script west_350131400.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350131500, 1): (350131400, 0),  # Heroes of Maple / Display Hall 2 -> Heroes of Maple / Display Hall 1 (map id in script name)
    (350131600, 1): (350131400, 0),  # Heroes of Maple / Display Hall 3 -> Heroes of Maple / Display Hall 1 (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
