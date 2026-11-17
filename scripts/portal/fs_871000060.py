# Portal script fs_871000060.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (871000056, 3): (871000060, 1),  # Querencia / Cleared Land Near Forest -> Querencia / Cleared Land Near Forest (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
