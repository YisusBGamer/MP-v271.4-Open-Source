# Portal script to_800021050.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800021108, 1): (800021050, 0),  # Oni Forest / Gyuki&apos;s Rest -> Oni Forest / Gyuki&apos;s Rest (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
