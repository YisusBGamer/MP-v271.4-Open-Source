# Portal script pt_410007026.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007021, 3): (410007026, 1),  # Shangri-La / Plum Blossom-Scented Spring -> Shangri-La / Blooming Spring 1 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
