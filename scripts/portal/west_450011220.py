# Labyrinth of Suffering Core Peak (450011220), left door.
# Traces in the Endless Labyrinth (35718): the maze (993063010).
# Growing Despair (35719): the fallen soldiers (993063028).
# Irrevocable (35720): Azalin further in (993063032).
import random

MAZE_RECORD = 35757

if sm.hasQuest(35718) and sm.getQRValue(MAZE_RECORD, "do") != "finish":
    door = sm.getQRValue(MAZE_RECORD, "door")
    if door is None or door == "":
        sm.setQRValue(MAZE_RECORD, "door", str(random.randint(1, 8)))
    sm.warp(993063010, 0)
elif sm.hasQuestCompleted(35718) and not sm.hasQuestCompleted(35719):
    sm.warp(993063028, 0)
elif sm.hasQuestCompleted(35719) and not sm.hasQuest(35720) and not sm.hasQuestCompleted(35720):
    sm.warp(993063032, 0)
else:
    sm.warp(450011530, 2)
sm.dispose()
