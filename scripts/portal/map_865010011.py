# Portal script map_865010011.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (865010012, 3): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map id in script name)
    (865010013, 3): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map id in script name)
    (865010014, 3): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map id in script name)
    (865010015, 3): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map id in script name)
    (865010016, 3): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map id in script name)
    (865010017, 3): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map id in script name)
    (865010018, 3): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map id in script name)
    (865010019, 3): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map id in script name)
    (865010020, 3): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
