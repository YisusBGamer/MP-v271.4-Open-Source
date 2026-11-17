# Portal script outMansion.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (931010010, 1): (310000000, 0),  # Edelstein / Guarded Mansion -> Black Wing Territory / Edelstein (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
