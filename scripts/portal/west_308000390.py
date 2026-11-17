# Portal script west_308000390.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (308000400, 1): (308000390, 0),  # Somewhere in Sharenian / Sharenian Palace Storm Drain -> Somewhere in Sharenian / Sharenian Palace Storm Drain (map id in script name)
    (308000410, 1): (308000390, 0),  # Somewhere in Sharenian / Sharenian Palace Storm Drain -> Somewhere in Sharenian / Sharenian Palace Storm Drain (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
