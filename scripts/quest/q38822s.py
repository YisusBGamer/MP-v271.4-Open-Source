# 38822 [Heliseum] Restoring the Blue Dragon Cannon - start (Eurenth). Plays the quest's official lines and hands over
# Eurenth's Crumply Note (4037223), the repair plans taken to Edea (nothing else gives them).
ITEM = 4037223

if sm.questStartDialogue(parentID, 3001002):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
