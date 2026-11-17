# Portal script rand_ola.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (109030003, 12): (109030003, 9),  # Hidden Street / Ola Ola &lt;Level 3&gt; -> Hidden Street / Ola Ola &lt;Level 3&gt; (reverse link)
    (109030003, 16): (109030003, 10),  # Hidden Street / Ola Ola &lt;Level 3&gt; -> Hidden Street / Ola Ola &lt;Level 3&gt; (reverse link)
    (109030003, 22): (109030003, 8),  # Hidden Street / Ola Ola &lt;Level 3&gt; -> Hidden Street / Ola Ola &lt;Level 3&gt; (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
