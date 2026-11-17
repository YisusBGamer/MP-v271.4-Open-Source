# 20036 Preparation for Knighthood (Atan 1541202): for a quest started but not ended (q20036s ends it at once).
execfile("scripts/quest/_mihile_story.py")

if not sm.canHold(MEDAL) or not sm.canHold(SPROUT_POTION, 50):
    sm.chatScript("Make room in your inventory.")
elif not sm.hasQuestCompleted(parentID):
    knighthood()
sm.dispose()
