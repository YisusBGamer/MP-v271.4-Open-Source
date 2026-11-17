# Zero's story (Classic Zeros; Swordie Plus version - see zgen/gen_story.py). I: Where Time Stopped (40960, chapter): opens the chapter and its first quest, in Mirror World Ludibrium
if sm.questStartDialogue(parentID):
    if sm.getQRValue(40608) != "1":
        sm.setQRValue(40608, "1")
    if not sm.hasQuest(40600) and not sm.hasQuestCompleted(40600):
        sm.startQuestNoCheck(40600)
    sm.chatScript("Go to Ludibrium in Mirror World (the Temporal Gate) and thank Hera and Mera.")
sm.dispose()
