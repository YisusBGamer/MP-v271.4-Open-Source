# Sinkhole Sunk (22860): King Omen is defeated - back to the HQ, where the quest is turned in
sm.chatScript("King Omen is defeated. Return to the Hideout.")
if sm.getFieldID() == 331004000:
    sm.warpInstanceOut(331001000, 0)
sm.dispose()
