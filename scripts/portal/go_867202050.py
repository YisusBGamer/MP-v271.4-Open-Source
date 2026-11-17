# Portal script go_867202050.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867201991, 1): (867202050, 0),  # Abrup Basin / Grim Vale Cliffs -> Abrup Basin / Grim Vale Cliffs (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
