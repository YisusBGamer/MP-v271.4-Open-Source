# Portal script bossSlime_enter.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (100051037, 2): (160070000, 4),  # Partem / Extra Creepy Crawly Copse 3 -> Ramuramu Valley / Ramuramu Hot Springs (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
