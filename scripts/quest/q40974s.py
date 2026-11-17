# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). V: More than Truth (40974, chapter): to the Mirror World Control Room - Will is waiting
if sm.questStartDialogue(parentID):
    if sm.getQRValue(40711) != "1":
        sm.setQRValue(40711, "1")
    if not sm.hasQuest(40705) and not sm.hasQuestCompleted(40705):
        sm.startQuestNoCheck(40705)
    sm.chatScript("Talk to Zero in Zero's Temple.")
sm.dispose()
