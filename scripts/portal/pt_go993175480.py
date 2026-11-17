# Portal script pt_go993175480.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (993175400, 11): (993175480, 0),  # Haunted Mansion / Central Hall -> Haunted Mansion / Haunted Mansion Entrance (map in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
