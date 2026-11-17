# Portal script: finish_WZXn0
# Maps: 400051800 (out_finish, pt 7), 400051900 (out_finish, pt 7), 400052000 (out_finish, pt 7), 400052100 (out_finish, pt 7), 400052200 (out_finish, pt 7)
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
