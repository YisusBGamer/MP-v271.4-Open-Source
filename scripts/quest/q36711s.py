# 36711 Thanks to the God-King - start (Ksha). Plays the quest's official lines and hands over Teapot (4037091), the item the quest
# is handed in with (nothing else gives it).
ITEM = 4037091

if sm.questStartDialogue(parentID, 3006227):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
