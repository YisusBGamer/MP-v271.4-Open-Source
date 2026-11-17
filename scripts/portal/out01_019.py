# Portal script out01_019.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (811000019, 3): (811000021, 1),  # Hieizan / East Tower Central Shrine Entrance -> Hieizan / Path to West Tower (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
