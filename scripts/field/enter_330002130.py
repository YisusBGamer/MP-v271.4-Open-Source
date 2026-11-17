# Hidden Street: The Big Moment (330002130), entered from the Sunset Lot's pt_sp1hidden portal.
# The original scene isn't in the client data: the quest's flag is set and the player goes back.
QUEST = 33046

if sm.hasQuest(QUEST):
    sm.setQRValue(QUEST, "ok", "1")
    sm.chatScript("Cygnus has struck out without warning. What just happened?")
# deferred: a warp straight from onUserEnter can leave the client on the loading screen
sm.invokeAfterDelay(1500, "warp", 330001900, 4, False)  # Sunset Lot, at pt_sp1hidden (False: warp now, not queued)
sm.dispose()
