# Portal script go_867202910.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867202900, 1): (867202910, 0),  # Abrup Basin / Grim Vale Canyon End -> Abrup Basin / Grim Vale Canyon End (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
