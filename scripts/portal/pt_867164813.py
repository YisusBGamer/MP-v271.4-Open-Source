# Portal script pt_867164813.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (867164812, 49): (867164813, 0),  # <Night Troupe> / Ludibrium Elevator Boarding Area -> <Night Troupe> / Ludibrium Observation Deck (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
