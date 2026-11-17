# Wing Master (24091, Mercedes story): Orchid is defeated (the quest has no NPC to report to)
sm.completeQuest(parentID)
sm.chatScript("Orchid is defeated... The Black Mage really is coming back.")
if sm.getFieldID() == 931000650:
    sm.warpInstanceOut(310040210, 0)
sm.dispose()
