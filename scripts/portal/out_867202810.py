# Portal script out_867202810.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (867202810, 1): (867202200, 0),  # Abrup Basin / Crimson Snow-Covered Windsleep Forest Junction -> Abrup Basin / Skuas Entrance (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
