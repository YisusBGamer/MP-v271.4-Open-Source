# Ericsson (2012018) | Orbis Park: the way to Chryse
sm.setSpeakerID(2012018)
if sm.sendAskYesNo("Michaela is looking for help with her pet Xerxes in Chryse. She's waiting at "
                   "#bSpiegelmann's Guest House#k. Shall I take you there?"):
    sm.warp(910002000, 0)
sm.dispose()
