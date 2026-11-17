# 66929 The Name of the Healer: Lynn 4th job advancement (17211 -> 17212) once the quest is done (no script of its own does it).
if chr.getJob() == 17211:
    if sm.questJobAdvance(parentID, 17211, 17212):
        sm.chatScript("You have advanced to Lynn (4th job).")
sm.dispose()
