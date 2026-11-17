# Portal script go_867201220.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867201200, 2): (867201220, 0),  # Abrup Basin / Windsleep Forest Entrance -> Abrup Basin / Windsleep Forest Trail (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
