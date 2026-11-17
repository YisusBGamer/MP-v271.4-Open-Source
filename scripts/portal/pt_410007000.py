# Portal script pt_410007000.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007001, 2): (410007000, 2),  # Odium / General Area by the Castle's Gate -> Odium / Residential District (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
