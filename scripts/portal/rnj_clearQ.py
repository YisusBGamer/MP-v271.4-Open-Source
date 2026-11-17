# Magatia (261000000) yurete00: the door to Yulete's Lab (926130000), where his requests start (3365, Lv. 70).
if chr.getLevel() >= 70:
    sm.warp(926130000, 2)
else:
    sm.chat("The door to Yulete's Lab is locked tight.")
sm.dispose()
