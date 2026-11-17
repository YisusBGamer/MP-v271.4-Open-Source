# Portal script pt_tengumemo5.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800024004, 3): (800024480, 1),  # Old Memories / Lost Memory -> Old Memories / Final Memory (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
