# Portal script glpqPortal01.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (610030500, 2): (610030540, 1),  # Party Quest / The Test of Unity -> Party Quest / Bowman Mastery Room (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
