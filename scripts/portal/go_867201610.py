# Portal script go_867201610.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867201600, 2): (867201610, 0),  # Abrup Basin / Grim Vale Cave -> Abrup Basin / Grim Vale Twisted Trail (map id in script name)
    (867201602, 1): (867201610, 0),  # Abrup Basin / Grim Vale Small Cave -> Abrup Basin / Grim Vale Twisted Trail (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
