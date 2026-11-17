# Portal script pt_410007015.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007011, 3): (410007015, 1),  # Odium / Laboratory Middle Corridor -> Odium / Laboratory Behind Locked Door 1 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
