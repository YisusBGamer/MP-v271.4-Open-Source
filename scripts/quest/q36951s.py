# 36951 A Letter to the Empress - start (Tru). Plays the quest's official lines and hands over Old Letter (4037307), the item the quest
# is handed in with (nothing else gives it).
ITEM = 4037307

if sm.questStartDialogue(parentID, 1002104):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
