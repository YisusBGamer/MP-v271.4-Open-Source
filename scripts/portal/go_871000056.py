# Portal script go_871000056.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (871000054, 4): (871000056, 2),  # Querencia / Forest Outside of Town -> Querencia / Cleared Land Near Forest (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
