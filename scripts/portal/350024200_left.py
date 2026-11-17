# Portal script 350024200_left.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350024200, 1): (350024100, 1),  # The Lumiere / Waist Deck -> The Lumiere / Conference Room (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
