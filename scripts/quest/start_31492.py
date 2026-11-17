# Wounded Victory (31492) has no start script: "If you accept, you will automatically move to the relevant field."
# (Act.img: Tyrant's Castle Entryway.) The castle gate there leads into the foyer (portal/enter_citadel.py).
if sm.hasQuest(parentID) and sm.getFieldID() != 401050000:
    sm.warp(401050000, 0)
sm.dispose()
