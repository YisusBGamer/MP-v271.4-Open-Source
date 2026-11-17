# 65958 A Step Forward: Mo Xuan 3rd job advancement (17510 -> 17511) once the quest is done (no script of its own does it).
if chr.getJob() == 17510:
    if sm.questJobAdvance(parentID, 17510, 17511):
        sm.chatScript("You have advanced to Mo Xuan (3rd job).")
sm.dispose()
