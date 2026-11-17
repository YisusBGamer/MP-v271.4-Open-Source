# The Afterlands: from a land's first field back to the land (x10 -> x00), arriving by its "goField" portal.
TO = {867113210: (867113200, 3), 867113310: (867113300, 1), 867113410: (867113400, 3), 867113510: (867113500, 2)}
t = TO.get(sm.getFieldID())
if t is not None:
    sm.warp(t[0], t[1])
sm.dispose()
