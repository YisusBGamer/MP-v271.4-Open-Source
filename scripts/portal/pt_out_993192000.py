# Portal script pt_out_993192000.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (993192007, 1): (993192000, 0),  # Blooming Forest / Blooming Forest Meadow -> Blooming Forest / Blooming Forest Meadow (map in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
