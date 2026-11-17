# Portal script pt_350053650_1.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350053700, 1): (350053650, 0),  # Black Heaven Inside / Black Heaven Deep Hold -> Black Heaven Inside / Black Heaven Deep Hold (map id in script name)
    (350053750, 1): (350053650, 0),  # Black Heaven Inside / Black Heaven Deep Hold -> Black Heaven Inside / Black Heaven Deep Hold (map id in script name)
    (350053800, 1): (350053650, 0),  # Black Heaven Inside / Black Heaven Deep Hold -> Black Heaven Inside / Black Heaven Deep Hold (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
