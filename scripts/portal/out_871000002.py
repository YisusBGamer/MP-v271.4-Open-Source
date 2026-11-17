# Portal script out_871000002.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (871000016, 1): (871000002, 0),  # Querencia / Forest Outside of Town -> Querencia / Forest Outside of Town (map in the script's name)
    (871000017, 2): (871000002, 0),  # Querencia / Forest Outside of Town -> Querencia / Forest Outside of Town (map in the script's name)
    (871000018, 2): (871000002, 0),  # Querencia / Forest Outside of Town -> Querencia / Forest Outside of Town (map in the script's name)
    (871000023, 1): (871000002, 0),  # Querencia / Forest Outside of Town -> Querencia / Forest Outside of Town (map in the script's name)
    (871000024, 1): (871000002, 0),  # Querencia / Forest Outside of Town -> Querencia / Forest Outside of Town (map in the script's name)
    (871000025, 1): (871000002, 0),  # Querencia / Forest Outside of Town -> Querencia / Forest Outside of Town (map in the script's name)
    (871000058, 2): (871000002, 0),  # Querencia / Construction Site Inside the Forest -> Querencia / Forest Outside of Town (map in the script's name)
    (871000059, 2): (871000002, 0),  # Querencia / Garden in the Forest -> Querencia / Forest Outside of Town (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
