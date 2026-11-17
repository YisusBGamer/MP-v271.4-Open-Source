# Portal script west_308000300.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (308000310, 1): (308000300, 0),  # Somewhere in Sharenian / Royal Palace Inner Passage -> Somewhere in Sharenian / Royal Palace Outer Passage (map id in script name)
    (308000320, 1): (308000300, 0),  # Somewhere in Sharenian / Sharenian Palce Storm Drain -> Somewhere in Sharenian / Royal Palace Outer Passage (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
