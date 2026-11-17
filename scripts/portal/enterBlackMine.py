# Portal from Dry Road: Road to the Mine 2 to Dry Road: Mine Entrance
# Vita's Disappearance (Resistance 23143): Vita is found at the Mine Entrance (931000620, your own copy) until the
# record (23149 = 1) is set.
if sm.hasQuest(23143) and sm.getQRValue(23149) != "1":
    sm.warpInstanceIn(931000620, 0)
else:
    sm.warp(310040200, 0)
sm.dispose()
