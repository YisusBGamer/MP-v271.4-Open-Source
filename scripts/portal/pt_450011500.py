# Labyrinth of Suffering Core (450011500), right door.
# Reunited with an Alliance Member (35713): the door leads to where the tied-up member is (993063007).
if sm.hasQuestCompleted(35712) and not sm.hasQuest(35713) and not sm.hasQuestCompleted(35713):
    sm.warp(993063007, 0)
else:
    sm.warp(450011510, 1)
sm.dispose()
