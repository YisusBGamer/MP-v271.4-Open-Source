# 36922 10 Boogies's Request - start (10 Boogies). Plays the quest's official lines and hands over her report (4037296), which is delivered
# to Neinheart (nothing else gives it).
ITEM = 4037296

if sm.questStartDialogue(parentID, 1103004):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
