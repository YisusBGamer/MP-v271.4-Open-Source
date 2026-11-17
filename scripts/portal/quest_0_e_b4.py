# Portal script quest_0_e_b4.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867117100, 10): (867117920, 1),  # Aspire Industries / Aspire Industries B4 -> Aspire Industries / Aspire Industries B4 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
