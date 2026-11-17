# Portal script pt_867112100.
# Destinations come from the game's map data (the portal's own target, the portal on the other side that
# leads here, or the room's exit to its town).
DEST = {
    (867112110, 1): (867112100, 0),  # MMF Henesys Stage / MMF -> MMF Show Entry Line / MMF (map id in script name)
    (867112120, 1): (867112100, 0),  # MMF Temple of Time Stage / MMF -> MMF Show Entry Line / MMF (map id in script name)
    (867112130, 1): (867112100, 0),  # MMF Ariant Stage / MMF -> MMF Show Entry Line / MMF (map id in script name)
    (867112140, 1): (867112100, 0),  # MMF Kerning City Stage / MMF -> MMF Show Entry Line / MMF (map id in script name)
    (867112150, 1): (867112100, 0),  # MMF Sleepywood Stage / MMF -> MMF Show Entry Line / MMF (map id in script name)
}

dest = DEST.get((sm.getFieldID(), parentID))
if dest is not None:
    sm.warp(dest[0], dest[1])
sm.dispose()
