# Gold Beach Ticket (2431238), from [Gold Beach] Initiate R&R Protocol: straight to Gold Beach.
GOLD_BEACH = 120040000

if sm.getFieldID() == GOLD_BEACH:
    sm.chat("You're already at Gold Beach.")
else:
    sm.consumeItem()
    sm.warp(GOLD_BEACH)
sm.dispose()
