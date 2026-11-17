# Portal script enterDisguise1.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (130010000, 5): (130010010, 2),  # Empress' Road / Training Forest I -> Empress' Road / Tino's Forest (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
