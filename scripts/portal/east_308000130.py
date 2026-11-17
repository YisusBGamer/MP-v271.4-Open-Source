# Portal script east_308000130.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (308000140, 2): (308000130, 0),  # Somewhere in Sharenian / Mole Hills -> Somewhere in Sharenian / Mole Hills (map id in script name)
    (308000150, 2): (308000130, 0),  # Somewhere in Sharenian / Mole Hills -> Somewhere in Sharenian / Mole Hills (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
