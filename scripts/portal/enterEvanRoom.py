# Portal script enterEvanRoom.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (100030101, 2): (100030100, 1),  # Utah&apos;s House / Living Room -> Utah&apos;s House / Small Attic (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
