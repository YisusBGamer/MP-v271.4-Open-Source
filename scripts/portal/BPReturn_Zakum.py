# Portal from zakum back to previous map
if sm.getReturnField() > 0:
    sm.warp(sm.getReturnField())
else:
    sm.warp(100000000, 20)