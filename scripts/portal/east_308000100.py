# Portal script east_308000100.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (308000110, 2): (308000100, 0),  # Somewhere in Sharenian / Mole Waterfall -> Somewhere in Sharenian / Mole Waterfall (map id in script name)
    (308000120, 2): (308000100, 0),  # Somewhere in Sharenian / Mole Waterfall -> Somewhere in Sharenian / Mole Waterfall (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
