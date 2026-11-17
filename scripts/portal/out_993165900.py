# The Second Void (993165900): leaving before the guardians are gone. [Karote] The Second Statue (38209) stays in progress;
# the tower's way up (or the quest's start) leads back in.
FLOOR = 410005003

if sm.sendAskYesNo("Leave the Void? The statue's memory stays hidden until every guardian here is defeated."):
    sm.warpInstanceOut(FLOOR, 0)
sm.dispose()
