# Cygnus Garden (993166407): the briefing for the final test is cut short - Ereve Intruder (36460).
if not sm.hasQuestCompleted(36460):
    sm.startQuestNoCheck(36460)
    sm.completeQuestNoCheck(36460)
sm.chatScript("Neinheart rushes in: an intruder has breached Ereve!")
sm.invokeAfterDelay(2000, "warp", 993166409, 0)
