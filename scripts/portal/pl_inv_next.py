# Portal script pl_inv_next.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867116900, 2): (867117154, 1),  # Aspire Industries / Lobby -> 867117154 (reverse link)
    (867116950, 1): (867117155, 1),  # Aspire Industries / Aspire Industries B1 -> 867117155 (reverse link)
    (867117000, 2): (867117156, 1),  # Aspire Industries / Aspire Industries B2 -> 867117156 (reverse link)
    (867117050, 1): (867117157, 1),  # Aspire Industries / Aspire Industries B3 -> 867117157 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
