# 38324 Dad's Too Busy 3 - start (Haram). Plays the quest's official lines and hands over Small Battery (4036895), the item the quest
# is handed in with (nothing else gives it).
ITEM = 4036895

if sm.questStartDialogue(parentID, 3005536):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
