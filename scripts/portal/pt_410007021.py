# Portal script pt_410007021.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007018, 2): (410007021, 1),  # Odium / The Wellspring -> Shangri-La / Plum Blossom-Scented Spring (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
