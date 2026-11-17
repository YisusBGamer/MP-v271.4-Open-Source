# Portal script go_867154300.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867154000, 5): (867154300, 0),  # &lt;Night Troupe&gt; / Dreams and Illusions Festival Venue -> &lt;Night Troupe&gt; / Circus Stage (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
