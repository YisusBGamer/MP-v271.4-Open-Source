# Portal script pt_101074000b.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (101074001, 2): (101074000, 0),  # Midsummer Night's Forest / Path from Ellinel -> Midsummer Night's Forest / Path to Ellinel (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
