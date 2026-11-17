# Remote Forest Plain (993201060) -> Remote Forest Clearing (993201061), from the third training session (36606) on.
# The first time in, the sparrows and a Stray Dog play out (993201003).
execfile("scripts/quest/_mihile_story.py")

if sm.hasQuest(36606) and sm.getQRValueByKey(STORY_QR, "clearing") is None:
    sm.setQRValue(STORY_QR, "clearing", "1")
    sm.warpInstanceIn(CLEARING_SCENE, 0)
elif sm.hasQuest(36606) or sm.hasQuestCompleted(36606):
    sm.warp(FOREST_CLEARING, 1)
else:
    sm.chatScript("Father told you not to go any further than this.")
sm.dispose()
