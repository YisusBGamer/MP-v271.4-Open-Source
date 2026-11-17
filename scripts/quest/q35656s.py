# [Genesis Weapon] Power Draw (35656): Grendel explains how to unleash the weapon; the quest has no NPC to hand in to, so it completes right away (the Traces of Magnus quest needs it).
if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.questStartDialogue(parentID)
    if sm.hasQuest(parentID):
        sm.completeQuestNoCheck(parentID)
sm.dispose()
