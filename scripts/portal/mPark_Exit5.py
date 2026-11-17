# Portal script: mPark_Exit5
# Maps: 993188700 (mPark_Exit5, pt 11), 993188800 (mPark_Exit5, pt 11), 993188900 (mPark_Exit5, pt 11)
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
