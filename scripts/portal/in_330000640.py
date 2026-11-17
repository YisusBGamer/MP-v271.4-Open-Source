# Portal script in_330000640.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (330000640, 2): (330000650, 1),  # Old Shinsoo School Building / Classroom -> Old Shinsoo School Building / School Roof (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
