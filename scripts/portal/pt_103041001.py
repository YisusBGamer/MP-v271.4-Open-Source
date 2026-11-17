# Portal script pt_103041001.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (103041001, 2): (103041002, 1),  # Kerning Tower / Blake's Office -> Kerning Tower / Rehearsal Room (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
