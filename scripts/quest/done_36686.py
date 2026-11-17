# 36686 After That Day: Khali 3rd job advancement (15410 -> 15411) once the quest is done (no script of its own does it).
if chr.getJob() == 15410:
    if sm.questJobAdvance(parentID, 15410, 15411):
        sm.chatScript("You have advanced to Khali (3rd job).")
sm.dispose()
