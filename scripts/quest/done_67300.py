# 67300 Star Guardian: Sia Astelle 4th job advancement (18211 -> 18212) once the quest is done (no script of its own does it).
if chr.getJob() == 18211:
    if sm.questJobAdvance(parentID, 18211, 18212):
        sm.chatScript("You have advanced to Sia Astelle (4th job).")
sm.dispose()
