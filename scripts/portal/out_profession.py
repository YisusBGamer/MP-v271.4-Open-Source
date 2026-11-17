# 910001000 Ardentmill exit: back where you came from (getReturnField is 0 when nothing was saved), else Henesys
rf = sm.getReturnField()
if rf > 0 and rf != sm.getFieldID():
    sm.warp(rf)
else:
    sm.warp(100000000, 20)
