# Portal script go867201800.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867201760, 1): (867201800, 0),  # Abrup Basin / Windsleep Forest Trap -> Abrup Basin / Cabin Front Porch (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
