# 36911 Victoria or Bust - start (Lilin). Plays the quest's official lines and hands over Lilin's Letter (4037292), which the
# quest is handed in with (nothing else gives it).
ITEM = 4037292

if sm.questStartDialogue(parentID, 1201000):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
