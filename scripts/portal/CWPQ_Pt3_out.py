# Portal script: CWPQ_Pt3_out
# Maps: 930100310 (out00, pt 7), 930100320 (out00, pt 7), 930100330 (out00, pt 7), 930100340 (out00, pt 7), 930100350 (out00, pt 7)
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
