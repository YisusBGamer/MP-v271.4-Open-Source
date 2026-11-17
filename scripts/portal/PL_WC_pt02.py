# Portal script PL_WC_pt02.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867123001, 2): (867123010, 1),  # Snowdrift Lodge / Lobby -> Snowdrift Lodge / Hotel Suite (reverse link)
    (867123001, 4): (867123020, 1),  # Snowdrift Lodge / Lobby -> Snowdrift Lodge / Hotel Suite (reverse link)
    (867123001, 5): (867123030, 1),  # Snowdrift Lodge / Lobby -> Snowdrift Lodge / Hotel Suite (reverse link)
    (867123001, 6): (867123040, 1),  # Snowdrift Lodge / Lobby -> Snowdrift Lodge / Hotel Suite (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
