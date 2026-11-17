# 36709 Please meet my son. - start (Simer). Plays the quest's official lines and hands over Gift Sack and Letter (4037088), the item the quest
# is handed in with (nothing else gives it).
ITEM = 4037088

if sm.questStartDialogue(parentID, 3006226):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
