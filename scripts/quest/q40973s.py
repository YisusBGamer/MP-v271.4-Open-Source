# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). IV: The Right Choice (40973, chapter): Beta demands the truth - in the Lost Time, where she waits
if sm.questStartDialogue(parentID):
    if sm.getQRValue(40710) != "1":
        sm.setQRValue(40710, "1")
    if not sm.hasQuest(40704) and not sm.hasQuestCompleted(40704):
        sm.startQuestNoCheck(40704)
    sm.warpInstanceIn(328090310, 0)
sm.dispose()
