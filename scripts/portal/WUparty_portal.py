# First Time Together (Cross World): on to the next stage (once it's cleared)
from net.swordie.ms.world.field.instance.instancehelper.pq import FirstTimeTogetherInstanceHelper as FT
inst = chr.getInstance()
helper = inst.getInstanceHelper() if inst is not None else None
if not isinstance(helper, FT):
    helper = None
if helper is not None:
    helper.next(chr)
sm.dispose()
