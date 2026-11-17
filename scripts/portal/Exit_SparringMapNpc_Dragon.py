# Portal script Exit_SparringMapNpc_Dragon.
# Beasts of Fury sparring/training area -> back to the Drill Hall.
# The duels are your own copy of the map (npc/5657x_VS); leave it properly.
if chr.getInstance() is not None:
    sm.warpInstanceOut(706041003, 0)
else:
    sm.warp(706041003, 0)
sm.dispose()
