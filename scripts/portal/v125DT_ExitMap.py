# Portal script v125DT_ExitMap.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (706050500, 1): (706050450, 2),  # Wu Tien Mountain / 1,500m Up: Mountainside -> Wu Tien Mountain / 1,300m Up (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
