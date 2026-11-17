# Portal script SleepWood_out.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (867201200, 1): (867201050, 0),  # Abrup Basin / Windsleep Forest Entrance -> Abrup Basin / Svarti Entrance (exit to return map)
    (867201220, 1): (867201050, 0),  # Abrup Basin / Windsleep Forest Trail -> Abrup Basin / Svarti Entrance (exit to return map)
    (867201240, 2): (867201050, 0),  # Abrup Basin / Glistening Sunlight Trail -> Abrup Basin / Svarti Entrance (exit to return map)
    (867201260, 1): (867201050, 0),  # Abrup Basin / Onerous Trail -> Abrup Basin / Svarti Entrance (exit to return map)
    (867201280, 1): (867201050, 0),  # Abrup Basin / Glistening Sunlight Trail -> Abrup Basin / Svarti Entrance (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
