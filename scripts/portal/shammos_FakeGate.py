# Resurrection of the Hoblin King: leaving
from net.swordie.ms.world.field.instance.instancehelper.pq import HoblinKingInstanceHelper as HK
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, HK):
    helper = None
if helper is not None and helper.isFinished():
    sm.warpInstanceOut(HK.BACKSTREET, 0)
elif sm.sendAskYesNo("Are you sure you want to leave? You won't be able to return."):
    sm.warpInstanceOut(HK.BACKSTREET, 0)
