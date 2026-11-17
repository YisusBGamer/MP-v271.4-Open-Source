# Portal script script_out.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350013200, 3): (350013200, 12),  # Orchid&apos;s Mindscape / Mindscape 2 -> Orchid&apos;s Mindscape / Mindscape 2 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
