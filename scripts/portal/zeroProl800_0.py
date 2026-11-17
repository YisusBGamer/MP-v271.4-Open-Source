# Portal script zeroProl800_0.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (321000800, 1): (321000700, 1),  # Shadowvale / Knights Barracks -> Umbra Temple / Shadowvale (reverse link)
    (321000800, 2): (321000700, 2),  # Shadowvale / Knights Barracks -> Umbra Temple / Shadowvale (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
