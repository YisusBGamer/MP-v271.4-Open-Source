# Portal script hid_224000103.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (224000082, 3): (224000103, 0),  # Korean Folk Town / Forbidden Cliff -> Korean Folk Town / Forbidden Cliff (map id in script name)
    (224000092, 3): (224000103, 0),  # Korean Folk Town / Forbidden Cliff -> Korean Folk Town / Forbidden Cliff (map id in script name)
    (224000096, 3): (224000103, 0),  # Korean Folk Town / Forbidden Cliff -> Korean Folk Town / Forbidden Cliff (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
