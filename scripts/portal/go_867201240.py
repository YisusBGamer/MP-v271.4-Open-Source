# Portal script go_867201240.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867201220, 2): (867201240, 0),  # Abrup Basin / Windsleep Forest Trail -> Abrup Basin / Glistening Sunlight Trail (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
