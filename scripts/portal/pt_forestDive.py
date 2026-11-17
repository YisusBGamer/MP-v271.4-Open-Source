# Portal script pt_forestDive.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (993192000, 2): (993192100, 1),  # Blooming Forest / Blooming Forest Meadow -> Blooming Forest / Blooming Garden (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
