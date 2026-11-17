# Portal script pt_410007020.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007024, 2): (410007020, 1),  # Shangri-La / Bamboo-Resting Winter -> Shangri-La / Sinners' Paradise (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
