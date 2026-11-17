# Portal script in_240010502.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (240010000, 5): (240010502, 0),  # Minar Forest / West Leafre Forest -> Hidden Street / Shinsoo's Refuge (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
