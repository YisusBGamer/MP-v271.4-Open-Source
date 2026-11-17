# Portal script pt_herbfox.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800023015, 4): (800023120, 1),  # Fox Mountain / Fox&apos;s Bluff -> Fox&apos;s Bluff / Foxwhisker Herb (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
