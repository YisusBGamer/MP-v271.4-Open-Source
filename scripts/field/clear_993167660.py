# Tyrant's Castle 3rd Floor (993167660) cleared: Velderoth fled; back to Edea in the Downtown Black Market
BLACK_MARKET = 401040000

if chr.getInstance() is not None:
    sm.chatScript("Velderoth fled. Report to Edea in the Downtown Black Market.")
    sm.invokeAfterDelay(3000, "warpInstanceOut", BLACK_MARKET, 0)
sm.dispose()
