# Princess No's Temple: back down to the previous floor
fieldID = sm.getFieldID()
if 811000200 <= fieldID <= 811000500:
    sm.warp(fieldID - 100, 0)
sm.dispose()
