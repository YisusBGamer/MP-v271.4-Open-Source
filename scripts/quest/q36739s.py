# 36739 A Chance to Turn the Tables - start (Cartalion). Plays the quest's official lines and hands over Sealed Document (4037221), the item the quest
# is handed in with (nothing else gives it).
ITEM = 4037221

if sm.questStartDialogue(parentID, 3000000):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
