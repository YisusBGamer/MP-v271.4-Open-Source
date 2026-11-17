# Portal script out_awakeMap.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (993184000, 1): (993184100, 5),  # Mountain of Awakening / Where the Sages Sleep -> Mountain of Awakening / Free-Form Training Grounds (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
