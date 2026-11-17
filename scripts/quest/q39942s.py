# 39942 Report on Ebonstar - start (Ebonstar Magician). Plays the quest's official lines and hands over Ebonstar Report (4037175), the item the quest
# is handed in with (nothing else gives it).
ITEM = 4037175

if sm.questStartDialogue(parentID, 3004543):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
