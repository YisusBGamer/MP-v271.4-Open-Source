# Portal script: pt_out_993197000
# Maps: 993197000 (west00, pt 7), 993197001 (west00, pt 7), 993197002 (west00, pt 7), 993197003 (west00, pt 7), 993197004 (west00, pt 7)
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
