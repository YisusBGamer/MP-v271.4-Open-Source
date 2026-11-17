# Labyrinth of Suffering Core 1 (450011510), left road.
# Rat in the Swamp (35716): the road leads to the Shimmering Wall (993063009).
if sm.hasQuestCompleted(35715) and not sm.hasQuest(35716) and not sm.hasQuestCompleted(35716):
    sm.warp(993063009, 0)
else:
    sm.warp(450011500, 2)
sm.dispose()
