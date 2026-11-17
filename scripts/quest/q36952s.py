# 36952 Neinheart the Tactician - start (Lilin, Rien). Plays the quest's official lines and hands over Lilin's letter to
# the Empress (4037305), which Neinheart takes (nothing else gives it).
ITEM = 4037305

if sm.questStartDialogue(parentID, 1201000):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
