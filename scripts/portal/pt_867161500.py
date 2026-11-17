# Portal script pt_867161500.
# Destinations come from the game's map data (the portal on the other side that leads here, or a map named in
# the script's name).
DEST = {
    (867161200, 7): (867161500, 0),  # <Night Troupe> / Night Troupe Festival Venue -> <Night Troupe> / Tangyoon's Crazy Feast Venue (map in the script's name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
