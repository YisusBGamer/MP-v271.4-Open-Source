# Portal script pt_410007032.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007023, 3): (410007032, 1),  # Shangri-La / Chrysanthemum-Blooming Autumn -> Shangri-La / Drowsy Autumn 1 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
