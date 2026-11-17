# Portal script pt_410013602_in.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410013624, 1): (410013602, 1),  # Tallahart / Silent Ashlands 5 -> Tallahart / Bridge to the City (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
