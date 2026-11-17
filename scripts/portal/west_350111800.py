# Portal script west_350111800.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350111900, 2): (350111800, 0),  # Heroes of Maple / Slumbering Dragon Island, Inside -> Heroes of Maple / Slumbering Dragon Island, Inside (map id in script name)
    (350112000, 2): (350111800, 0),  # Heroes of Maple / Slumbering Dragon Island, Inside -> Heroes of Maple / Slumbering Dragon Island, Inside (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
