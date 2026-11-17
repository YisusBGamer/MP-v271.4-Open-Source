# Breaking and Entering - 1F (31517) has no start script; its accept text says
# "If you accept, you will automatically move to the relevant field."
# (Act.img: Darkheart Workshop 2nd Floor Stairs.) The stairs up: portal/portal_victor2.py.
if sm.hasQuest(parentID) and sm.getFieldID() != 401051003:
    sm.warp(401051003, 0)
sm.dispose()
