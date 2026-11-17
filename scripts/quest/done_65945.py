# 65945 Touch of the Divine: Mo Xuan 2nd job advancement (17500 -> 17510) once the quest is done (no script of its own does it).
if chr.getJob() == 17500:
    if sm.questJobAdvance(parentID, 17500, 17510):
        sm.chatScript("You have advanced to Mo Xuan (2nd job).")
sm.dispose()
