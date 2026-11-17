# [Genesis Weapon] Power Unbound, Stage 1 (35666): the first level of the weapon's power is unleashed; completes right away (the Traces of Damien quest needs it).
if not sm.hasQuestCompleted(parentID):
    if not sm.hasQuest(parentID):
        sm.questStartDialogue(parentID)
    if sm.hasQuest(parentID):
        sm.completeQuestNoCheck(parentID)
        chr.chatMessage("[Genesis Weapon] The first level of the Genesis Weapon's power has been unleashed.")
sm.dispose()
