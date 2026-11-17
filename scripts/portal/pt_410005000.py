# Portal script pt_410005000.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410003000, 3): (410005000, 1),  # Hotel Arcus / Hotel Arcus Saloon -> Hotel Arcus / Hotel Arcus 2nd Floor (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
