# Portal script 101073110_out.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (101073110, 1): (101072000, 0),  # Mandraky Field / Desolate Orchard 2 -> Ellinel Fairy Academy / Ellinel Academy Lobby (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
