# Portal script: pt_battleout01
# Maps: 866010460 (out00, pt 7), 866010470 (out00, pt 7), 866010480 (out00, pt 7), 866500000 (out00, pt 7), 866501000 (out00, pt 7)
# Exit portal: uses the map return/forced-return target from WZ.

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
