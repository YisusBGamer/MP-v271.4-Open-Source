# Portal script outAfrienMemory.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (900030000, 1): (914100021, 0),  # Afrien's Memory  / Behind the Stronghold  -> Slumbering Dragon Island  / Cave of Silence (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
