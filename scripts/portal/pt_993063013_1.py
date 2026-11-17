# Labyrinth of Suffering Core maze (Traces in the Endless Labyrinth, 35718): a last-room door.
# One of the eight last doors leads out (record 35757 "door", rolled at the maze entrance); the rest end in a dead end.
MAZE_RECORD = 35757
DOOR = 1

exit_door = sm.getQRValue(MAZE_RECORD, "door")
if exit_door is None or exit_door == "":
    exit_door = str(DOOR)
if str(DOOR) == exit_door:
    sm.setQRValue(MAZE_RECORD, "do", "finish")
    sm.chat("You found a way through the Endless Labyrinth.")
    sm.warp(450011220, 7)
else:
    wrong = DOOR if DOOR < int(exit_door) else DOOR - 1
    sm.warp(993063016 + wrong, 0)
sm.dispose()
