# Portal script out_q34915.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (940205000, 1): (402000615, 0),  # Refuge Outskirts / Caravan Refuge -> Refuge Outskirts / Beyond the Refuge (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
