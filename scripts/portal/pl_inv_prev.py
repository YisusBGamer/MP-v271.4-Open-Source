# Portal script pl_inv_prev.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867116950, 3): (867117154, 2),  # Aspire Industries / Aspire Industries B1 -> 867117154 (reverse link)
    (867117000, 1): (867117155, 2),  # Aspire Industries / Aspire Industries B2 -> 867117155 (reverse link)
    (867117050, 3): (867117156, 2),  # Aspire Industries / Aspire Industries B3 -> 867117156 (reverse link)
    (867117100, 1): (867117157, 2),  # Aspire Industries / Aspire Industries B4 -> 867117157 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
