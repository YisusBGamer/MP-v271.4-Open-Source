# Portal script 620100053_west00.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (620100053, 2): (620100052, 2),  # Deserted Island Number 3 / Southeastern Beach -> Deserted Island Number 3 / Southern Beach (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
