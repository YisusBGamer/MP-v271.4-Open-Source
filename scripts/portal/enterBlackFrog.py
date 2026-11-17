# 220000300 Ludibrium Village, scr00: the Frog House, where Evan meets the Black Wings (22581 and later);
# Rage (22596): back to teach Gentleman a lesson (a copy of the house of its own).
if sm.hasQuest(22596):
    sm.warpInstanceIn(922030001, 0)
elif sm.hasHadQuest(22581) and not sm.hasQuestCompleted(22596):
    sm.warp(922030000, 1)
else:
    sm.chat("The door is locked.")
sm.dispose()
