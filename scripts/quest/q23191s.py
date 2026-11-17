# 23191 [Verne Mine] Those Who Resist - start (Wonny). Plays the quest's official lines and hands over Gabrielle's Intel (4037289), which is delivered
# to Claudine (nothing else gives it).
ITEM = 4037289

if sm.questStartDialogue(parentID, 2152018):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
