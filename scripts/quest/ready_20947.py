# [Mission] Defeat the Puppeteer and Master of Disguise (20947): done - back to 10 Boogies on the Strolling Path
sm.completeQuest(parentID)
sm.chatScript("You defeated them! Report back to 10 Boogies.")
if sm.getFieldID() == 930030005:
    sm.warpInstanceOut(310030000, 0)
sm.dispose()
