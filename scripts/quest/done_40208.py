# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Done with quest 40208:
if not sm.hasQuest(40209) and not sm.hasQuestCompleted(40209):
    sm.startQuestNoCheck(40209)
sm.chatScript("Larelle is in a cave beyond the Northern Forest Path (Henesys dungeon portal).")
if sm.getFieldID() == 323090320:
    sm.warpInstanceOut(323000000, 10)
sm.dispose()
