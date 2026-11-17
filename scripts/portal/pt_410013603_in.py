# Portal script pt_410013603_in.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410013642, 2): (410013603, 1),  # Tallahart / Fate-Fields of Providence 3 -> Tallahart / Altar of Providence (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
