# Portal script pt_867168001.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (867168000, 8): (867168001, 0),  # <Night Troupe> / Night Troupe Mu Lung Festival Venue -> <Night Troupe> / Moonlit Mu Lung Dojo Entrance (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
