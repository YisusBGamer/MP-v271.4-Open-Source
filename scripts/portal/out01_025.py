# Portal script out01_025.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (811000025, 1): (811000028, 2),  # Hieizan / West Tower Central Shrine Entrance -> Hieizan / Inside Small Shrine (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
