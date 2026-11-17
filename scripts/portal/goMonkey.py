# Portal script goMonkey.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (809060000, 6): (809060100, 1),  # Golden Temple / Golden Temple -> Mini Dungeon / Monkey Temple 1 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
