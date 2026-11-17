# Portal script pt_410007601_in.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007600, 8): (410007601, 0),  # Carcion / Eye of the Ishfira -> Carcion / Temple of Tears (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
