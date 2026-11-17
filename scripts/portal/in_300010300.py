# Portal script in_300010300.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (300010420, 2): (300010300, 0),  # Ellin Forest / Deep Inside the Cave -> Ellin Forest / Beyond the Cave (map in the script's name)
    (300020000, 2): (300010300, 1),  # Ellin Forest / Deep Inside the Cave -> Ellin Forest / Beyond the Cave (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
