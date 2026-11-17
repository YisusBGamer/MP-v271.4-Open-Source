# Portal script pt_867112200.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867112220, 1): (867112200, 0),  # MMF Temple of Time Stage / MMF -> MMF Henesys Stage / MMF (map id in script name)
    (867112240, 1): (867112200, 0),  # MMF Ariant Stage / MMF -> MMF Henesys Stage / MMF (map id in script name)
    (867112260, 1): (867112200, 0),  # MMF Kerning City Stage / MMF -> MMF Henesys Stage / MMF (map id in script name)
    (867112280, 1): (867112200, 0),  # MMF Sleepywood Stage / MMF -> MMF Henesys Stage / MMF (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
