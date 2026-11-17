# Portal script pt_410013606_in.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410013600, 11): (410013606, 2),  # Tallahart / Grave of the Gods -> Tallahart / Sanctum Gate (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
