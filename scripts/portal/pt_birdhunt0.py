# Portal script pt_birdhunt0.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350020100, 1): (350020110, 1),  # The Lumiere / Front Ship Deck -> The Lumiere / Central Ship Deck (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
