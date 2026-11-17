# Portal script in_306050300.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (306050300, 2): (306050400, 1),  # Gate / Dragon Master&apos;s Tower -> Gate / Tower Interior (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
