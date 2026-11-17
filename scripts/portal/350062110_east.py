# Portal script 350062110_east.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350062130, 1): (350062110, 0),  # Black Heaven Inside / Black Heaven Corridors 3 -> Black Heaven Inside / Black Heaven Corridors 2 (map id in script name)
    (350062150, 1): (350062110, 0),  # Black Heaven Inside / Black Heaven Corridors 4 -> Black Heaven Inside / Black Heaven Corridors 2 (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
