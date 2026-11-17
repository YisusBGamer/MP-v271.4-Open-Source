# Portal script muk_0001_0011.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (875000001, 4): (875000011, 1),  # Xuanshan / Xuanshan School -> Xuanshan / Xuanshan Mountain Vantage (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
