# Portal script pt_410007035.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007024, 3): (410007035, 2),  # Shangri-La / Bamboo-Resting Winter -> Shangri-La / Harsh Winter 1 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
