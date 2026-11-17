# Portal script p_610063170.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (610063430, 1): (610063170, 0),  # Crimsonwood Keep of the Past / Versal  -> Crimsonwood Keep of the Past / Crimsonwood Keep  (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
