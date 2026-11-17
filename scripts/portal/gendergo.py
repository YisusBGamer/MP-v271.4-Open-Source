# Portal script gendergo.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (670010200, 15): (670010200, 3),  # Hidden Street / Stage 1 - Magik Mirror -> Hidden Street / Stage 1 - Magik Mirror (reverse link)
    (670010200, 16): (670010200, 4),  # Hidden Street / Stage 1 - Magik Mirror -> Hidden Street / Stage 1 - Magik Mirror (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
