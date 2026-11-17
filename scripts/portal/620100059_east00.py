# Portal script 620100059_east00.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (620100059, 1): (620100052, 3),  # Deserted Island Number 3 / Southwestern Beach -> Deserted Island Number 3 / Southern Beach (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
