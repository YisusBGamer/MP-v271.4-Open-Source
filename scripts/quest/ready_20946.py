# [Mission] Defeat the Black Wing Henchman (20946): done - the kids are safe; back to Edelstein (the teddy bear)
sm.completeQuest(parentID)
sm.chatScript("You saved the kids! Report back to the teddy bear in Edelstein.")
if sm.getFieldID() == 930030001:
    sm.warpInstanceOut(310000000, 0)
sm.dispose()
