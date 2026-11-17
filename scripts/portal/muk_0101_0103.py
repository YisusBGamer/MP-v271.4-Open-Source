# Portal script muk_0101_0103.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (875000101, 3): (875000103, 1),  # Xuanshan / Lower Waterfall Path -> Xuanshan / Waterfall Side Road (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
