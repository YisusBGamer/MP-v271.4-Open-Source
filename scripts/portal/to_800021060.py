# Portal script to_800021060.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (800021117, 1): (800021060, 1),  # Zipangu / Nightmare&apos;s End -> Zipangu / Armory (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
