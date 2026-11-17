# Portal script in_410004111.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410004110, 2): (410004111, 1),  # Vallora / Below Deck -> Vallora / Supply Warehouse (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
