# 36962 Bringing Back the Brain - end (Maha). Aran 2nd job advancement 2100 -> 2110.
sm.setSpeakerID(1201002)
sm.sendNext("You remember Sir Blacksmith's test? And how you got me? Aran! Your memory really is coming back!")
if sm.questJobAdvance(parentID, 2100, 2110):
    sm.sendSayOkay("I restored some of your old abilities. Your level isn't what it used to be, so train hard and "
                   "get back to the old you!")
sm.dispose()
