# Portal script pt_310070485_1.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (310070487, 1): (310070485, 0),  # Black Heaven / Black Heaven Inside -> Black Heaven / Black Heaven Inside (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
