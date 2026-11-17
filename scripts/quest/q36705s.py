# 36705 Little Priest's Request 2 - start (Hydar). Plays the quest's official lines and hands over Refined Sword (4037086), the item the quest
# is handed in with (nothing else gives it).
ITEM = 4037086

if sm.questStartDialogue(parentID, 3006222):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
