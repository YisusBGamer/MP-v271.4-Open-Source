# Portal script intoTeacher.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (330000010, 7): (330001000, 1),  # Shinsoo International School / Hallway -> Shinsoo International School / Staff Room (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
