# Portal script muk_2000_2100.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (875002000, 2): (875002100, 1),  # Spirit's Defile / Devil Clan Stronghold Entrance -> Spirit's Defile / Twisting Caverns 1 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
