# Portal script DLep4_02_Out.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (306020100, 1): (306020000, 0),  # Desolate Moor / Escapeway -> Lion King's Castle / Escapeway (exit to return map)
    (306020200, 1): (306020000, 0),  # Desolate Moor / Escapeway -> Lion King's Castle / Escapeway (exit to return map)
    (306020300, 1): (306020000, 0),  # Desolate Moor / Escapeway -> Lion King's Castle / Escapeway (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
