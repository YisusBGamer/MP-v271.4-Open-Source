# Portal script goSkuasEnter.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867202300, 3): (867233000, 2),  # Abrup Basin / Skuas -> Abrup Basin / Skuas Forward Defense Line (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
