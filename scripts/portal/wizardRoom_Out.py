# Portal script wizardRoom_Out.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (101000005, 2): (101000000, 0),  # Ellinia / Hall of Magicians -> Ellinia / Ellinia (exit to the map's return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
