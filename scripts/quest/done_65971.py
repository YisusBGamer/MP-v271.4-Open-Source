# 65971 History Repeats: Mo Xuan 4th job advancement (17511 -> 17512) once the quest is done (no script of its own does it).
if chr.getJob() == 17511:
    if sm.questJobAdvance(parentID, 17511, 17512):
        sm.chatScript("You have advanced to Mo Xuan (4th job).")
sm.dispose()
