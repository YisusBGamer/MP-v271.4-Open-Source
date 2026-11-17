# Portal script gate_intoFuture.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (270000000, 5): (271000000, 3),  # Time Lane / Three Doors -> Gate to the Future / Gate to the Future (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
