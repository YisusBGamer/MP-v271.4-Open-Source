# Portal script pt_410007029.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (410007022, 3): (410007029, 2),  # Shangri-La / Orchid Leaf-Green Summer -> Shangri-La / Gentle Summer 1 (reverse link)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
