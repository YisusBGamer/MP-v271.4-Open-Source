# 38711 The Turtles' Goodwill - start (Eto). Plays the quest's official lines and hands over Herb Sack (4037199), the item the quest
# is handed in with (nothing else gives it).
ITEM = 4037199

if sm.questStartDialogue(parentID, 3006517):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
