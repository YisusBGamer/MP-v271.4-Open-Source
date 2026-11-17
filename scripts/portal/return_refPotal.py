# Portal script return_refPotal.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (931050820, 1): (240000000, 0),  # Hidden Street / Leafre Mystic Field -> Minar Forest / Leafre (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
