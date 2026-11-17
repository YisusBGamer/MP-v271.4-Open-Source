# Portal script in_cabin01.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (200090900, 5): (200090910, 5),  # On Voyage / Victoria Bound -> On Voyage / Cabin - Victoria Bound (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
