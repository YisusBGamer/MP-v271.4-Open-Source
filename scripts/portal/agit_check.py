# Portal script agit_check.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (400020100, 6): (400020300, 1),  # West Pantheon / West Pantheon Plains -> West Pantheon / Heliseum Hideout (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
