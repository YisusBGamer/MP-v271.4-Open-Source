# Portal script muk_0100_0106.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (875000100, 3): (875000106, 1),  # Xuanshan / Xuanshan Crossroad -> Xuanshan / Steep Hill (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
