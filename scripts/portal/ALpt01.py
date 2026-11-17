# The Afterlands: the shortcut between the field's two levels (pt01 -> pt00).
PT = {867113210: (3, 4), 867113240: (3, 4), 867113270: (2, 3), 867113310: (3, 4), 867113340: (3, 4), 867113370: (2, 3), 867113410: (3, 4), 867113440: (3, 4), 867113470: (2, 3), 867113510: (3, 4), 867113540: (3, 4), 867113570: (2, 3)}
p = PT.get(sm.getFieldID())
if p is not None:
    sm.teleportToPortal(p[0])
sm.dispose()
