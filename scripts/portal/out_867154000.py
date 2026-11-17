# Portal script out_867154000.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867154001, 1): (867154000, 0),  # &lt;Night Troupe&gt; / Dreams and Illusions Festival Venue -> &lt;Night Troupe&gt; / Dreams and Illusions Festival Venue (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
