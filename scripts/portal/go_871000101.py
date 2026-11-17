# Portal script go_871000101.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (871000097, 2): (871000101, 1),  # Querencia / Deep Inside the Town Forest -> Querencia / Deep Inside the Town Forest (map in the script's name, portal back)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
