# Portal script muk_1104_1001.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (875001104, 4): (875001001, 1),  # Hejin / Back Alley -> Hejin / Docks (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
