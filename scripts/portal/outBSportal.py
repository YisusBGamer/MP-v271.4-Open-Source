# Portal script: outBSportal
# Maps: 960000000 (out00, pt 7), 960000100 (out00, pt 7)
# Exit portals: listed WZ exits use the map return/forced-return target.

EXITS = {
    (960000100, 3),
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
