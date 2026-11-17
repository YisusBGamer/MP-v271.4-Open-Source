# Portal script pt_410013620_in.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410013601, 1): (410013620, 3),  # Tallahart / Ruins -> Tallahart / Silent Ashlands 1 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
