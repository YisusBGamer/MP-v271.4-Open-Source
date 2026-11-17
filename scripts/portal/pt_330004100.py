# Portal script pt_330004100.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (330003004, 1): (330004100, 0),  # Old Shinsoo International School Building / Art Room -> Old Shinsoo International School Building / Classroom (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
