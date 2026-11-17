# 66911 Earth Guardian Pan: Lynn 1st job advancement (17001 -> 17200) once the quest is done (no script of its own does it).
if chr.getJob() == 17001:
    if sm.questJobAdvance(parentID, 17001, 17200):
        sm.chatScript("You have advanced to Lynn (1st job).")
sm.dispose()
