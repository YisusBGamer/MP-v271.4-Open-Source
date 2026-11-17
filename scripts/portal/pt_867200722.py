# Portal script pt_867200722.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867200725, 1): (867200722, 0),  # Abrup Basin / Edge Wind Snowfield -> Abrup Basin / Windy Spot (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
