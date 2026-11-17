# Portal script 940200330_out00.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (940200330, 1): (450005100, 0),  # Arcana / Near the Floral Flute -> Arcana / Near the Floral Flute (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
