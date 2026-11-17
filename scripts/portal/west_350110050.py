# Portal script west_350110050.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350110100, 2): (350110050, 0),  # Heroes of Maple / Slumbering Dragon Island, Outside -> Heroes of Maple / Slumbering Dragon Island, Outside (map id in script name)
    (350110150, 2): (350110050, 0),  # Heroes of Maple / Slumbering Dragon Island, Outside -> Heroes of Maple / Slumbering Dragon Island, Outside (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
