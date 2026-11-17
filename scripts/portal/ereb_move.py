# Portal script ereb_move.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (303000130, 10): (303000000, 0),  # Ereve / Heading to: Mu Lung -> Ereve / Cygnus&apos;s Rest (own target)
    (303000130, 11): (303000000, 0),  # Ereve / Heading to: Mu Lung -> Ereve / Cygnus&apos;s Rest (own target)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
