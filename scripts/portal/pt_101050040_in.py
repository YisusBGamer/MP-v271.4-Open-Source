# Portal script pt_101050040_in.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (101050000, 12): (101050040, 1),  # Fairy Forest / Elluel -> Fairy Forest / Lucid's Room (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
