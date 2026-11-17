# Portal script pt_410005001.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410003140, 1): (410005001, 2),  # Hotel Arcus / Nostalgic Drive-in Theater 6 -> Karote / The Unending Tower (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
