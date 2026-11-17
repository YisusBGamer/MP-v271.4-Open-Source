# Portal script in_15st_Museum.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (993050000, 6): (993050900, 7),  # Maple 14th Street Fair / Maple 14th Street Fair -> Maple 14th Street Fair / Ark Museum (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
