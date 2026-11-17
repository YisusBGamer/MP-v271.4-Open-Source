# Portal script pt00_876000700.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (876000800, 8): (876000700, 0),  # Zipangu / World's End -> Zipangu / World's End (map in the script's name)
    (876004700, 8): (876000700, 0),  # Zipangu / World's End -> Zipangu / World's End (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
