# Portal script target_867201822.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867201820, 1): (867201822, 0),  # Abrup Basin / Windsleep Forest Treetops -> Abrup Basin / Windsleep Forest Heights (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
