# Dark Destiny (25660, Luminous story): Lania is free of the darkness (the quest has no NPC to report to)
sm.completeQuest(parentID)
sm.chatScript("You managed to save Lania from the darkness.")
if sm.getFieldID() == 350020008:
    sm.warpInstanceOut(101000100, 0)
sm.dispose()
