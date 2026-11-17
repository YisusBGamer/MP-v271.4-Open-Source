# 67298 The Will to Face Darkness: Sia Astelle 3rd job advancement (18210 -> 18211) once the quest is done (no script of its own does it).
if chr.getJob() == 18210:
    if sm.questJobAdvance(parentID, 18210, 18211):
        sm.chatScript("You have advanced to Sia Astelle (3rd job).")
sm.dispose()
