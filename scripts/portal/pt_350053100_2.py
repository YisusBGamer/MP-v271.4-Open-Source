# Portal script pt_350053100_2.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350053300, 1): (350053100, 0),  # Black Heaven Inside / Black Heaven Deep Hold -> Black Heaven Inside / Black Heaven Deep Hold (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
