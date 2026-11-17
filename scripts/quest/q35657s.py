# [Genesis Weapon] Lingering Question and Fresh Start (35657): the journey's epilogue; completes right away.
if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.questStartDialogue(parentID)
    if sm.hasQuest(parentID):
        sm.completeQuestNoCheck(parentID)
sm.dispose()
