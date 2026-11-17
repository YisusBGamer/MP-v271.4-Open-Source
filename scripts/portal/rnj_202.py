# Romeo and Juliet party quest: from a Dark Lab back to the Special Lab
from net.swordie.ms.world.field.instance.instancehelper.pq import RomeoJulietInstanceHelper as RJ
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, RJ):
    helper = None
if helper is not None:
    sm.warp(helper.stage4(), 0)
sm.dispose()
