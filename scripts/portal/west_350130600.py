# Portal script west_350130600.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350130700, 3): (350130600, 0),  # Heroes of Maple / Utility Tunnel 3 -> Heroes of Maple / Utility Tunnel 2 (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
