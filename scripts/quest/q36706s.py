# 36706 Little Priest's Request 3 - start (Shadi). Plays the quest's official lines and hands over Refined Sword (4037095), the item the quest
# is handed in with (nothing else gives it).
ITEM = 4037095

if sm.questStartDialogue(parentID, 3006223):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
