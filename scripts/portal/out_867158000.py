# Portal script out_867158000.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (867158001, 5): (867158000, 0),  # <Night Troupe> / Dreams and Illusions Festival Venue -> <Night Troupe> / Dreams and Illusions Festival Venue (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
