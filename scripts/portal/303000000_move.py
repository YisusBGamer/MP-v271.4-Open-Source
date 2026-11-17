# Portal script 303000000_move.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (303010000, 1): (303000000, 0),  # Leafre / Nix&apos;s Forest -> Ereve / Cygnus&apos;s Rest (map id in script name)
    (303020000, 4): (303000000, 0),  # Aqua Road / Pearl Ocean -> Ereve / Cygnus&apos;s Rest (map id in script name)
    (303030000, 1): (303000000, 0),  # Mu Lung / Mr. Do&apos;s Herb Field -> Ereve / Cygnus&apos;s Rest (map id in script name)
    (303090310, 1): (303000000, 0),  # Hidden Street / Cygnus&apos;s Recovery 1 -> Ereve / Cygnus&apos;s Rest (map id in script name)
    (303090330, 1): (303000000, 0),  # Hidden Street / Cygnus&apos;s Recovery 2 -> Ereve / Cygnus&apos;s Rest (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
