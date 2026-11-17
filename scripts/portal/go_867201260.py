# Portal script go_867201260.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867201240, 1): (867201260, 0),  # Abrup Basin / Glistening Sunlight Trail -> Abrup Basin / Onerous Trail (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
