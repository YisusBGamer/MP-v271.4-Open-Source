# The Afterlands: from a land to its first field (x00 -> x10), arriving by the field's "gohome" portal.
TO = {867113200: (867113210, 1), 867113300: (867113310, 1), 867113302: (867113310, 1), 867113400: (867113410, 1), 867113500: (867113510, 1)}
t = TO.get(sm.getFieldID())
if t is not None:
    sm.warp(t[0], t[1])
sm.dispose()
