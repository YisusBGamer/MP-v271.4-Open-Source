# Remnant of the Goddess: back to the Center Tower
from net.swordie.ms.world.field.instance.instancehelper.pq import GoddessInstanceHelper as GH
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, GH):
    helper = None
if helper is not None:
    sm.warp(GH.CENTER, 0)
sm.dispose()
