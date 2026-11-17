# Portal script muk_0107_2000.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (875000107, 2): (875002000, 1),  # Xuanshan / Candlewick Mountainside -> Spirit's Defile / Devil Clan Stronghold Entrance (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
