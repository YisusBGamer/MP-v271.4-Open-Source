# Portal script: BHdungeon_out
# Maps: 350011100 (pt_out, pt 7), 350011200 (pt_out, pt 7), 350011300 (pt_out, pt 7), 350011800 (pt_out, pt 7), 350011900 (pt_out, pt 7)
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
