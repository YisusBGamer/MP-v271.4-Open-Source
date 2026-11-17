# 36007 [Ariant] Save the Sultan - start (Jano). Plays the quest's official lines and hands over the Mandraky Antidote (4037408), which the
# quest is handed in with (nothing else gives it).
ITEM = 4037408

if sm.questStartDialogue(parentID, 2101010):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
