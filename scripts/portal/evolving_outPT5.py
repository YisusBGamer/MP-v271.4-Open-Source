# Portal script evolving_outPT5.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (957014000, 2): (957000000, 0),  # Evolution System / Link 5 -> Hidden Street / Evolution Lab (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
