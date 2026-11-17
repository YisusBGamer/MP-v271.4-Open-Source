# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). Done with quest 40503:
if sm.getQRValue(40511) != "1":
    sm.setQRValue(40511, "1")  # lets the next quest start
if not sm.hasQuest(40504) and not sm.hasQuestCompleted(40504):
    sm.startQuestNoCheck(40504)
sm.chatScript("A traitor among you... Look for clues back in Zero's Temple.")
sm.dispose()
