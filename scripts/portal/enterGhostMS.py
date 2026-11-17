# Portal script enterGhostMS.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (100020400, 5): (100020401, 1),  # Singing Mushroom Forest / Blue Mushroom Forest 2 -> Singing Mushroom Forest / Gentle Breeze Forest (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
