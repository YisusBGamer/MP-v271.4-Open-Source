# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Done with quest 40111:
if not sm.hasQuest(40112) and not sm.hasQuestCompleted(40112):
    sm.startQuestNoCheck(40112)
sm.chatScript("Return to Pietta in Zero's Temple.")
if sm.getFieldID() == 322090420:
    sm.warpInstanceOut(322000000, 11)
sm.dispose()
