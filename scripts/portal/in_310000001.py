# Portal script in_310000001.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (310000000, 20): (310000001, 1),  # Black Wing Territory / Edelstein -> Edelstein / Edelstein City Hall (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
