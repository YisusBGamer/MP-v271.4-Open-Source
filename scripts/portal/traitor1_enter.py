# Portal script traitor1_enter.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (400030300, 6): (400030600, 1),  # South Pantheon / Orange Forest -> South Pantheon / Heretic Temple Entrance (reverse link)
}

# Traitor in the Heretic Temple (25876) waits for this record to say "1"
if sm.hasQuest(25876):
    sm.startQuest(25916)
    sm.setQRValue(25916, "1")

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
