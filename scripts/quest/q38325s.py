# 38325 Kaya and Seri 1 - start (Kaya, Odium). Plays the quest's official lines and hands over the Energy Power
# (4036896), the medicine taken to Seri (nothing else gives it; Quest.wz: forfeit and accept again if it's lost).
ITEM = 4036896

if sm.questStartDialogue(parentID, 3005533):
    if not sm.hasItem(ITEM) and sm.canHold(ITEM, 1):
        sm.giveItem(ITEM)
sm.dispose()
