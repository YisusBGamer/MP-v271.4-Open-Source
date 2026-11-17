# Escaping the Mansion (39627, Adele) done: its record end=1, which Where Is Jerome? (39628) needs to start.
if sm.getQRValue(parentID, "end") != "1":
    sm.setQRValue(parentID, "end", "1")
sm.dispose()
