# Portal script: exit_viewpoint
# Maps: 993028000 (exit, pt 7), 993028010 (exit, pt 7), 993028020 (exit, pt 7), 993028030 (exit, pt 7), 993028040 (exit, pt 7)
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
