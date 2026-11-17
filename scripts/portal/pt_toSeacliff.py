# Portal script pt_toSeacliff.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (875010600, 2): (875010700, 1),  # Eternal Forest / Path to the Ocean Cliff -> Eternal Forest / Ocean Cliff (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
