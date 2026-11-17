# Portal script GoalPortal_RunningMap.
# Stone Bridge of Endurance (Beasts of Fury): the goal - back to your dojo's Drill Hall.
if sm.getFieldID() >= 706041035:
    sm.warp(706041008, 0)
else:
    sm.warp(706041003, 0)
sm.dispose()
