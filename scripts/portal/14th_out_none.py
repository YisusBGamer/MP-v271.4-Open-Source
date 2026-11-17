# Portal script: 14th_out_none
# Maps: 993010000 (out_extreme, pt 8), 993010000 (out_reverse, pt 8), 993010000 (out_icePlanet, pt 8), 993010000 (out_mission, pt 8), 993010000 (out_vita, pt 8)
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
