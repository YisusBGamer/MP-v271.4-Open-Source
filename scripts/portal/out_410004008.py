# Portal script out_410004008.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (410004008, 1): (410004000, 0),  # Narin / Magistrate&apos;s Storage Room -> Narin / Sun-Mottled Plateau (exit to the map's return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
