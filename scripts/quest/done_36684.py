# 36684 Rage Engraved Herein: Khali 2nd job advancement (15400 -> 15410) once the quest is done (no script of its own does it).
if chr.getJob() == 15400:
    if sm.questJobAdvance(parentID, 15400, 15410):
        sm.chatScript("You have advanced to Khali (2nd job).")
sm.dispose()
