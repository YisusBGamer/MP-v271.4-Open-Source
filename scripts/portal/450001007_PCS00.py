# Portal script 450001007_PCS00.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (450001310, 3): (450001007, 0),  # Hidden Street / Lake of Oblivion -> Lake of Oblivion / Lake of Oblivion (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
