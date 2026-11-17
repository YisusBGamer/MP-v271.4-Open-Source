# Portal script aswan_channelout.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (262000300, 1): (262010000, 0),  # Azwan / Occupy Mode Lobby -> Azwan / Azwan Obelisk (exit to the map's return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
