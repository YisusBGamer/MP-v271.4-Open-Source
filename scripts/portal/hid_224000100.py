# Portal script hid_224000100.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (224000062, 8): (224000100, 0),  # Korean Folk Town / Black Mountain Entrance -> Korean Folk Town / Black Mountain Entrance (map id in script name)
    (224000091, 4): (224000100, 0),  # Korean Folk Town / Black Mountain Entrance -> Korean Folk Town / Black Mountain Entrance (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
