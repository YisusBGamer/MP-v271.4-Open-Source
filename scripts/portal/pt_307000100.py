# Portal script pt_307000100.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (307000100, 5): (307000110, 2),  # Shadowvale / Shadowvale -> Shadowvale / Research Lab (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
