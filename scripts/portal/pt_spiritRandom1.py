# Spirit Savior (921172300) exit portals (pt_spiritRandom1 right, pt_spiritRandom2 left): leave the run
if sm.sendAskYesNo("Do you want to leave Spirit Savior? A run you leave doesn't count."):
    sm.warpInstanceOut(921172400, 0)
sm.dispose()
