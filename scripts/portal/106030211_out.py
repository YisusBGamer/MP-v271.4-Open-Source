# Portal script 106030211_out.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (106030211, 5): (106030000, 0),  # Mushroom Castle / Violetta's Garden 2 -> Mushroom Castle / Banquet Hall (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
