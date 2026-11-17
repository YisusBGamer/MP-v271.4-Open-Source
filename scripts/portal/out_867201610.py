# Portal script out_867201610.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (867201610, 1): (867201602, 0),  # Abrup Basin / Grim Vale Twisted Trail -> Abrup Basin / Grim Vale Small Cave (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
