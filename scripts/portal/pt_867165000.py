# Portal script pt_867165000.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (867164800, 1): (867165000, 0),  # <Night Troupe> / Night Troupe Ludibrium Festival Venue -> <Night Troupe> / Clickity-Clack Toy Factory (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
