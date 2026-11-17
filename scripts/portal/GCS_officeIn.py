# Portal script GCS_officeIn.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (870001000, 4): (870001002, 2),  # Guild Castle / Duke Dylan Castle Lobby -> Guild Castle / Duke's Office (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
