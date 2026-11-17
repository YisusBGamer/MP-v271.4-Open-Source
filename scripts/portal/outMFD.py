# Portal script: outMFD
# Maps: 330003002 (out00, pt 7), 330003100 (out00, pt 7), 330003200 (out00, pt 7), 330003300 (out00, pt 7), 330003400 (out00, pt 7)
# Exit portals: listed WZ exits use the map return/forced-return target.

EXITS = {
    (330003100, 1),
    (330003200, 1),
    (330003300, 1),
    (330003400, 1),
    (330003500, 1),
    (330003600, 1),
}

key = (field.getId(), parentID)
if key in EXITS:
    returnMap = field.getInfo().getReturnMap()
    if returnMap <= 0 or returnMap >= 999999999:
        returnMap = field.getInfo().getForcedReturn()
    if 0 < returnMap < 999999999 and returnMap != field.getId():
        if chr.getInstance() is not None:
            sm.warpInstanceOut(returnMap)
        else:
            sm.warp(returnMap)
    else:
        sm.dispose()
else:
    sm.dispose()
