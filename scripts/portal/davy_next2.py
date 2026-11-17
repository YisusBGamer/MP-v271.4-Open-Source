# Lord Pirate party quest: an old stage map (not used on live servers): on to stage 3
from net.swordie.ms.world.field.instance.instancehelper.pq import LordPirateInstanceHelper as LP
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, LP):
    helper = None
if helper is not None:
    sm.warp(LP.STAGE_3, 0)
sm.dispose()
