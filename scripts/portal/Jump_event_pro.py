# Portal script Jump_event_pro.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (709000604, 17): (209000000, 0),  # 709000604 -> Hidden Street / Happyville (exit to the map's return map)
    (709000604, 18): (209000000, 0),  # 709000604 -> Hidden Street / Happyville (exit to the map's return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
