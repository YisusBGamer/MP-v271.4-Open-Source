# Character field ID when accessed: 993176500
# ObjectID: 0
# ParentID: 12
# Portal Position X: 1544
# Portal Position Y: 43
# Maps: 993176500 (out_left, pt 7), 993176500 (out_right, pt 7)
# Exit portal: uses the map return/forced-return target from WZ.
# Rise Salon is an event town with returnMap/forcedReturn pointing at itself,
# so Map.wz records no destination for either side exit.

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
