# 36693 Dad's Letters: Khali 4th job advancement (15411 -> 15412) once the quest is done (no script of its own does it).
if chr.getJob() == 15411:
    if sm.questJobAdvance(parentID, 15411, 15412):
        sm.chatScript("You have advanced to Khali (4th job).")
sm.dispose()
