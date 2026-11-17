# Portal script goQuest_22575.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (211000001, 3): (211000000, 0),  # El Nath / Chief&apos;s Residence -> El Nath / El Nath (exit to the map's return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
