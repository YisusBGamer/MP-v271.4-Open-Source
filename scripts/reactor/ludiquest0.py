# The odd Doll's House (2202000) in the secret room (922000010): the monster from the Crack in Time that stole the
# Ludibrium Clocktower's pendulum. Breaking it recovers the pendulum for Dollhouse (3230); then back to Delv.
QUEST = 3230

if sm.hasQuest(QUEST) and sm.getQRValue(QUEST) != "dummy":
    sm.setQRValue(QUEST, "dummy", False)
    sm.chat("You found the pendulum inside the fake Doll's House! Take it back to Delv.")
    sm.invokeAfterDelay(2000, "warpInstanceOut", 221023200, 0)
sm.removeReactor()
sm.dispose()
