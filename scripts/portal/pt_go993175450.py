# Portal script pt_go993175450.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (993175400, 16): (993175450, 0),  # Haunted Mansion / Central Hall -> Haunted Mansion / Guest Room 5 (map in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
