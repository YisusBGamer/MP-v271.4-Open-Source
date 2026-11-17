# Portal script in_secretGarden.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (271030400, 3): (271030410, 1),  # Knight Stronghold / Knight District 4 -> Knight Stronghold / Secret Grove (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
