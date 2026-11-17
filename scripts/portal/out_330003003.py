# Portal script out_330003003.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (330003004, 2): (330003003, 0),  # Old Shinsoo International School Building / Art Room -> Old Shinsoo International School Building / School Infirmary (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
