# Portal script Exit_SparringMapNpc_Tiger.
# Beasts of Fury sparring/training area -> back to the Drill Hall.
# The duels are your own copy of the map (npc/5657x_VS); leave it properly.
if chr.getInstance() is not None:
    sm.warpInstanceOut(706041008, 0)
else:
    sm.warp(706041008, 0)
sm.dispose()
