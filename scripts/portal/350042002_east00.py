# Portal script 350042002_east00.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350042001, 1): (350042002, 0),  # Black Heaven / Inspection Station -> Black Heaven / Behind the Inspection Station (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
