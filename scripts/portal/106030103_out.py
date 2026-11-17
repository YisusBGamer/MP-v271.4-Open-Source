# Portal script: 106030103_out
# Maps: 106030103 (out00, pt 9), 106030103 (out01, pt 9), 106030103 (out02, pt 9), 106030103 (out03, pt 9), 106030103 (out04, pt 9)
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
