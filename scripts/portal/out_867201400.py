# Portal script out_867201400.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867201400, 1): (867201300, 0),  # Abrup Basin / Eyeful Treasure Trove -> Abrup Basin / Glistening Sunlight Trail (exit to the map's return map)
    (867201401, 1): (867201400, 0),  # Abrup Basin / Eyeful Treasure Trove -> Abrup Basin / Eyeful Treasure Trove (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
