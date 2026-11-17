# Portal script 2019CNY_evan.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (100030200, 2): (867136124, 3),  # Farm Street / Small Forest Trail -> Utah&apos;s House / Front Yard (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
