# Portal script west00_306030300.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (306030300, 6): (306030000, 2),  # Gate / Dragon Master&apos;s Tower -> Gate / Central Gate (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
