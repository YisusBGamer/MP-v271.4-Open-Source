# Portal script Enter_RP.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (811000099, 1): (811000014, 1),  # Hieizan / Hieizan Entrance -> Hieizan / East Towers Bells (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
