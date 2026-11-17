# Portal script goto_350013000.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (350013100, 1): (350013000, 0),  # Orchid&apos;s Mindscape / Mindscape 1 -> Resistance Headquarters / Hospital Room (map id in script name)
    (350013200, 7): (350013000, 0),  # Orchid&apos;s Mindscape / Mindscape 2 -> Resistance Headquarters / Hospital Room (map id in script name)
    (350013300, 1): (350013000, 0),  # Orchid&apos;s Mindscape / Mindscape 3 -> Resistance Headquarters / Hospital Room (map id in script name)
    (350013500, 5): (350013000, 0),  # Orchid&apos;s Mindscape / Mindscape 4 -> Resistance Headquarters / Hospital Room (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
