# 66925 Please Don't Forget Me: Lynn 3rd job advancement (17210 -> 17211) once the quest is done (no script of its own does it).
if chr.getJob() == 17210:
    if sm.questJobAdvance(parentID, 17210, 17211):
        sm.chatScript("You have advanced to Lynn (3rd job).")
sm.dispose()
