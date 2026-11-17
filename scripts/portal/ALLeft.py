# The Afterlands: back to the previous field (x70 -> x40 -> x10), arriving by its "right" portal.
TO = {867113240: (867113210, 2), 867113270: (867113240, 2), 867113340: (867113310, 2), 867113370: (867113340, 2), 867113440: (867113410, 2), 867113470: (867113440, 2), 867113540: (867113510, 2), 867113570: (867113540, 2)}
t = TO.get(sm.getFieldID())
if t is not None:
    sm.warp(t[0], t[1])
sm.dispose()
