# Crash Site (450011120), right door.
# Into the Labyrinth (35706): the door leads to where the Alliance's traces are (993063003).
if sm.hasQuest(35706):
    sm.warp(993063003, 0)
else:
    sm.warp(450011430, 1)
sm.dispose()
