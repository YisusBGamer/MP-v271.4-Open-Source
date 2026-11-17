# Portal script pt_350040110.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350040191, 2): (350040110, 0),  # Scrapyard / Android&apos;s Memory -> Scrapyard / Central Tower (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
