# Portal script in_260010601.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (260010600, 4): (260010601, 0),  # The Burning Sands / Tent of the Entertainers -> Hidden Street / Overlooked Area (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
