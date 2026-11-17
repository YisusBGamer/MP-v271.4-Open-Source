# Portal script exit_701220310.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (701220310, 1): (701220200, 0),  # Shaolin Temple / Sutra Depository 5-6 F -> Shaolin Temple / Sutra Depository 3-4 F (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
