# Portal script Pc_865010011 .
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (865010012, 19): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map in the script's name)
    (865010013, 19): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map in the script's name)
    (865010014, 19): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map in the script's name)
    (865010015, 19): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map in the script's name)
    (865010016, 19): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map in the script's name)
    (865010017, 19): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map in the script's name)
    (865010018, 19): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map in the script's name)
    (865010019, 19): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map in the script's name)
    (865010020, 19): (865010011, 0),  # Commerci Republic / The Scarlet Terror -> Commerci Republic / The Scarlet Terror (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
