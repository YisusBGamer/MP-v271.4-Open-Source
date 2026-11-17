# Portal script HW_portal_house.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (229010000, 2): (229000000, 5),  # Haunted Mansion / Haunted Mansion Entrance -> Haunted Mansion / Central Hall (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
