# Goodbye Seal (31510) has no start script: "If you accept, you will automatically move to the relevant field."
# (Act.img: Laboratory Entrance.) Pouring the reagent on Treglow's door: portal/enter_treDoor.py.
if sm.hasQuest(parentID) and sm.getFieldID() != 401052104:
    sm.warp(401052104, 0)
sm.dispose()
