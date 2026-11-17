# 36950 Decoding the Letter - start (Athena Pierce). Plays the quest's official lines and hands over the Old Letter (4037304), which the
# quest is handed in with (nothing else gives it).
ITEM = 4037304

if sm.questStartDialogue(parentID, 1012100):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
