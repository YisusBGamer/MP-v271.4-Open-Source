# Portal script next00_027.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (811000027, 1): (811000029, 1),  # West Tower Central Shrine / Inside West Tower Central Shrine 2 -> Small Shrine / Inside Small Shrine (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
