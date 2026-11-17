# Portal script pt_410007609_in.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007601, 1): (410007609, 0),  # Carcion / Temple of Tears -> Carcion / Sacred Tree Summit (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
