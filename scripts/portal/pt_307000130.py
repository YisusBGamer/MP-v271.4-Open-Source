# Portal script pt_307000130.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (307000110, 3): (307000130, 0),  # Shadowvale / Research Lab -> Shadowvale / Lab Storage 1 (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
