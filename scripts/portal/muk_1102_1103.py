# Portal script muk_1102_1103.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (875001102, 2): (875001103, 1),  # Hejin / Hejin Outskirts -> Hejin / Abandoned Warehouse (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
