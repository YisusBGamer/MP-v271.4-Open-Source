# Portal script: masteriaPre_out
# Maps: 600000100 (masteriaPre_out, pt 7), 600000101 (masteriaPre_out, pt 7), 600000102 (masteriaPre_out, pt 7), 600000104 (masteriaPre_out, pt 7), 600000105 (masteriaPre_out, pt 7)
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
