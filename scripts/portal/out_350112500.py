# Portal script out_350112500.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350112600, 1): (350112500, 0),  # Heroes of Maple / Slumbering Dragon Island, Inside -> Heroes of Maple / Slumbering Dragon Island, Inside (map id in script name)
    (350112700, 1): (350112500, 0),  # Heroes of Maple / Slumbering Dragon Island, Inside -> Heroes of Maple / Slumbering Dragon Island, Inside (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
