# 66917 Sky Guardian Penni: Lynn 2nd job advancement (17200 -> 17210) once the quest is done (no script of its own does it).
if chr.getJob() == 17200:
    if sm.questJobAdvance(parentID, 17200, 17210):
        sm.chatScript("You have advanced to Lynn (2nd job).")
sm.dispose()
