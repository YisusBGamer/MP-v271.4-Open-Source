# Portal script west_350132500.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350132600, 1): (350132500, 0),  # Heroes of Maple / Display Hall 2 -> Heroes of Maple / Display Hall 3 (map id in script name)
    (350132700, 1): (350132500, 0),  # Heroes of Maple / Display Hall 1 -> Heroes of Maple / Display Hall 3 (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
