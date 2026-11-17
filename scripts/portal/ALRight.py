# The Afterlands: to the next field of the land (x10 -> x40 -> x70), arriving by its "left" portal.
TO = {867113210: (867113240, 1), 867113240: (867113270, 1), 867113310: (867113340, 1), 867113340: (867113370, 1), 867113410: (867113440, 1), 867113440: (867113470, 1), 867113510: (867113540, 1), 867113540: (867113570, 1)}
t = TO.get(sm.getFieldID())
if t is not None:
    sm.warp(t[0], t[1])
sm.dispose()
