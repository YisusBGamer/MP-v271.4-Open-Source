# 67297 Those Who Serve Providence: Sia Astelle 2nd job advancement (18200 -> 18210) once the quest is done (no script of its own does it).
if chr.getJob() == 18200:
    if sm.questJobAdvance(parentID, 18200, 18210):
        sm.chatScript("You have advanced to Sia Astelle (2nd job).")
sm.dispose()
