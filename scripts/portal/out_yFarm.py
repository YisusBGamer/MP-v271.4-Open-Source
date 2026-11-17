# Portal script: out_yFarm
# Maps: 993033000 (out_left, pt 7)
# Leaves the farm town. When the character entered the farm through the farm UI the farm
# instance knows the map it came from, so it is used as the exit target.

if chr.getInstance() is not None:
    sm.warpInstanceOut()
    sm.dispose()
else:
    sm.warp(100000000)
    sm.dispose()
