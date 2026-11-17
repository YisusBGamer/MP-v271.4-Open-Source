# Portal script SleepWood2_out.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (867201700, 2): (867201603, 0),  # Abrup Basin / Cutoff Forest Trail -> Abrup Basin / Grim Vale Small Cave (exit to return map)
    (867201720, 2): (867201603, 0),  # Abrup Basin / Jellyrash Trail -> Abrup Basin / Grim Vale Small Cave (exit to return map)
    (867201740, 1): (867201603, 0),  # Abrup Basin / Windsleep Forest Trap -> Abrup Basin / Grim Vale Small Cave (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
