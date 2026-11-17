# Coliseum (2170006) | Xerxes in Chryse, Inside the Coliseum
sm.setSpeakerID(2170006)
if sm.sendAskYesNo("Do you want to leave the Coliseum and go back to Spiegelmann's Guest House?"):
    if chr.getInstance() is not None:
        sm.warpInstanceOut(910002000, 0)
    else:
        sm.warp(910002000, 0)
sm.dispose()
