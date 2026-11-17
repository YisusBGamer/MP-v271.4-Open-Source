# Portal script: pt_puzzle_out
# Maps: 350122000 (pt01, pt 7), 350122200 (pt01, pt 7), 350122400 (pt01, pt 7), 350122600 (pt01, pt 7)
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
