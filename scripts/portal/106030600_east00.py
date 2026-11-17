# 106030600 Storage Deck, east00: the Captain's Quarters - locked until the puzzle is pieced together (Secret Captain's
# Quarters, 30068). The fight with the Black Viking is in a copy of your own.
if sm.hasQuestCompleted(30068):
    sm.warpInstanceIn(106030800, 0)
else:
    sm.chatScript("The Captain's Quarters are sealed by a strange puzzle lock.")
sm.dispose()
