# Portal script go_867158004.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (867158000, 3): (867158004, 1),  # <Night Troupe> / Dreams and Illusions Festival Venue -> <Night Troupe> / Circus Stage (map in the script's name, portal back)
    (867158001, 3): (867158004, 0),  # <Night Troupe> / Dreams and Illusions Festival Venue -> <Night Troupe> / Circus Stage (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
