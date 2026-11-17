# Portal script HW_portal_GR1.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (229000000, 6): (229000010, 2),  # Haunted Mansion / Central Hall -> Haunted Mansion / Guest Room 1 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
