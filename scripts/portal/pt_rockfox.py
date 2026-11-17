# Portal script pt_rockfox.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800023015, 1): (800023100, 1),  # Fox Mountain / Fox&apos;s Bluff -> Fox&apos;s Bluff / The Rock Pile (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
