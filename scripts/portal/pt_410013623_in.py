# Portal script pt_410013623_in.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410013622, 1): (410013623, 2),  # Tallahart / Silent Ashlands 3 -> Tallahart / Silent Ashlands 4 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
