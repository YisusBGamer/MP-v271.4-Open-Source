# Portal script east_308000004.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (308000170, 1): (308000004, 0),  # Somewhere in Sharenian / Royal Palace Outer Passage -> Somewhere in Sharenian / Royal Palace Outer Passage (map id in script name)
    (308000190, 1): (308000004, 0),  # Somewhere in Sharenian / Royal Palace Outer Passage -> Somewhere in Sharenian / Royal Palace Outer Passage (map id in script name)
    (308090004, 1): (308000004, 0),  # Somewhere in Sharenian / Somewhere in Sharenian -> Somewhere in Sharenian / Royal Palace Outer Passage (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
