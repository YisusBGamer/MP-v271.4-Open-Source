# Locked Candle Chest ([Halloween] Episode 2: Lighting Repair): a Candle Chest Key dropped in front of it
# (SeasonalEvents#onItemDropped) opens it: one Candle.
CANDLE = 4034708

if sm.hasQuest(15115) and sm.canHold(CANDLE):
    sm.giveItem(CANDLE, 1)
    sm.chatScript("The chest clicks open. You found a Candle!")
sm.dispose()
