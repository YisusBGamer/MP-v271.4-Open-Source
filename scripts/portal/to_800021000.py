# Portal script to_800021000.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800021101, 1): (800021000, 0),  # Oni Forest / Oni Forest Entrance -> Oni Forest / Oni Forest Entrance (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
