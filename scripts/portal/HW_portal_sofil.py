# Portal script HW_portal_sofil.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (229000000, 11): (229000100, 1),  # Haunted Mansion / Central Hall -> Haunted Mansion / Sophelia's Room (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
