# Portal script go_dona.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (871000097, 3): (871000094, 2),  # Querencia / Deep Inside the Town Forest -> Querencia / Grandma Dona's House (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
