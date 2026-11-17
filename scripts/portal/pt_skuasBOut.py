# Portal script pt_skuasBOut.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, a map named in the script's name, or the room's exit to its town).
DEST = {
    (867233050, 1): (867233000, 0),  # Abrup Basin / Skuas Forward Defense Line -> Abrup Basin / Skuas Forward Defense Line (exit to return map)
    (867233100, 1): (867233000, 0),  # Abrup Basin / Skuas Forward Defense Line -> Abrup Basin / Skuas Forward Defense Line (exit to return map)
    (867233150, 1): (867233000, 0),  # Abrup Basin / Skuas Forward Defense Line -> Abrup Basin / Skuas Forward Defense Line (exit to return map)
    (867233500, 1): (867233000, 0),  # Abrup Basin / Destroyed Skuas Walls -> Abrup Basin / Skuas Forward Defense Line (exit to return map)
    (867233600, 1): (867233000, 0),  # Abrup Basin / Skuas -> Abrup Basin / Skuas Forward Defense Line (exit to return map)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
