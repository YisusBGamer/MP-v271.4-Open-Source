# Portal script pt_410007009.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007009, 2): (410007010, 1),  # Odium / Captured Alley 4 -> Odium / Awakened Laboratory (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
