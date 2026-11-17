# Portal script pt_350040100.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350040160, 1): (350040100, 0),  # Scrapyard / Refuge -> Scrapyard / Refuge (map id in script name)
    (350040160, 2): (350040100, 0),  # Scrapyard / Refuge -> Scrapyard / Refuge (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
