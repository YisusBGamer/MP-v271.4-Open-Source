# Portal script pt_867164840.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (867164800, 17): (867164840, 2),  # <Night Troupe> / Night Troupe Ludibrium Festival Venue -> <Night Troupe> / Ludibrium Elevator <Ascending> (map in the script's name, portal back)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
